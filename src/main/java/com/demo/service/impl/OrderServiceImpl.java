package com.demo.service.impl;

import com.demo.bean.card_order;
import com.demo.dao.CardCompanyRepository;
import com.demo.dao.CardNumberRepository;
import com.demo.dao.CardOrderRepository;
import com.demo.dao.CardTypeRepository;
import com.demo.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CardOrderRepository cardOrderRepository;
    @Autowired
    private CardNumberRepository cardNumberRepository;
    @Autowired
    private CardTypeRepository cardTypeRepository;
    @Autowired
    private CardCompanyRepository cardCompanyRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public String getOrderList(String startTime, String endTime, String wechatID, String username,
                               String phone, String address, String taocan_id, String taocan_name,
                               String card_name, String order_state, String express, String express_no,
                               String logistics, String company_name, String card_number, String pageIndex,
                               String pageSize) {
        logger.info("getOrderList接口接收参数:startTime=" + startTime + ",endTime=" + endTime + ",wechatID=" + wechatID + ",username=" + username + ",phone=" + phone + ",address=" + address
                + ",taocan_id=" + taocan_id + ",taocan_name=" + taocan_name + ",card_name=" + card_name + ",order_state=" + order_state + ",express=" + express + ",express_no=" + express_no
                + ",logistics=" + logistics + ",company_name=" + company_name + ",card_number=" + card_number + ",pageIndex=" + pageIndex + ",pageSize=" + pageSize);

        try {
            int intPageIndex;
            int intPageSize;
            if (pageSize == null || "".equals(pageSize)) {
                intPageSize = 10;
            }else if("all".equals(pageSize)){
                intPageSize = 100000;
            }  else {
                intPageSize = Integer.parseInt(pageSize);
            }
            if ("1".equals(pageIndex) || "".equals(pageIndex) || pageIndex == null) {
                intPageIndex = 0;
            }else {
                intPageIndex = (Integer.parseInt(pageIndex) - 1) * intPageSize;
            }
            List<card_order> orderList = cardOrderRepository.selectOrder(startTime, endTime, wechatID,
                    username, phone, address, taocan_id, taocan_name, card_name, order_state, express,
                    express_no, logistics, company_name, card_number, intPageIndex, intPageSize);
            int orderSize = cardOrderRepository.selectOrderSize(startTime, endTime, wechatID, username,
                    phone, address, taocan_id, taocan_name, card_name, order_state, express, express_no,
                    logistics, company_name, card_number);
            StringBuilder sb = new StringBuilder("{\"code\":\"200\",\"size\":\"" + orderSize + "\"," +
                    "\"content\":[");
            if (orderList.size() > 0) {
                for (card_order order : orderList) {
                    sb.append("{");
                    sb.append("\"orderId\":\"" + order.getId() + "\",");
                    sb.append("\"wechatId\":\"" + order.getWechatid().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                    sb.append("\"username\":\"" + order.getUsername().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                    sb.append("\"phone\":\"" + order.getPhone() + "\",");
                    sb.append("\"address\":\"" + order.getAddress().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                    sb.append("\"taocanName\":\"" + order.getTaocan_name().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                    sb.append("\"cardType\":\"" + order.getCard_name().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                    sb.append("\"companyName\":\"" + order.getCompany_name().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                    sb.append("\"cardNumber\":\"" + order.getCard_number().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                    sb.append("\"orderState\":\"" + order.getOrder_state() + "\",");
                    sb.append("\"insertDate\":\"" + order.getInsertdate() + "\",");
                    sb.append("\"express\":\"" + order.getExpress().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                    sb.append("\"expressNo\":\"" + order.getExpress_no().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                    sb.append("\"logistics\":\"" + order.getLogistics().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\"},");
                }
                if (sb.toString().contains("companyName")) {
                    sb = sb.deleteCharAt(sb.length() - 1);
                }
            }
            sb.append("]}");
            return sb.toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

    @Override
    public String updateOrder(String orderId,String phone, String address, String orderState, String express, String expressNo) {
        logger.info("updateOrder接口接收参数:orderId=" + orderId + ",phone=" + phone + ",address=" + address + "," +
                "orderState=" + orderState + ",express=" + express + ",expressNo=" + expressNo);

        try {
            Timestamp now = new Timestamp(new Date().getTime());
            card_order cardOrder = cardOrderRepository.getOne(Integer.valueOf(orderId));
            if (phone != null && !"".equals(phone)) {
                cardOrder.setPhone(phone.trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"",""));
            }
            if (address != null && !"".equals(address)) {
                cardOrder.setAddress(address.trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"",""));
            }
            if (orderState != null && !"".equals(orderState)) {
                cardOrder.setOrder_state(Integer.parseInt(orderState));
            }
            if (express != null && !"".equals(express)) {
                cardOrder.setExpress(express.trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"",""));
            }
            if (expressNo != null && !"".equals(expressNo)) {
                cardOrder.setExpress_no(expressNo.trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"",""));
            }
            cardOrder.setCreatedate(now);
            cardOrderRepository.save(cardOrder);
            return "{\"code\":\"200\",\"content\":\"修改成功\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

    @Override
    public String deleteOrder(String orderId) {
        logger.info("deleteOrder接口接收参数:orderId=" + orderId);

        try {
            int deleteResult = cardOrderRepository.deleteOrder(Integer.parseInt(orderId));
            if (deleteResult > 0) {
                return "{\"code\":\"200\",\"content\":\"删除成功\"}";
            } else {
                return "{\"code\":\"201\",\"content\":\"删除失败\"}";
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

    @Override
    public String login(String username, String password) {
        logger.info("login接口接收参数:username=" + username + ",password=" + password);

        if ("18686866648".equals(username) || "13717892457".equals(username)) {
            if ("123456".equals(password)) {
                return "{\"code\":\"200\",\"content\":\"登录成功\"}";
            } else {
                return "{\"code\":\"201\",\"content\":\"密码错误\"}";
            }
        } else {
            return "{\"code\":\"201\",\"content\":\"用户名不存在\"}";
        }
    }

    public String getOrderInfoByWeChatID(String wechatID) {
        logger.info("getOrderInfoByWeChatID接口接收参数:wechatID=" + wechatID);

        try {
            List<card_order> cardOrderList = cardOrderRepository.getOrderInfoByWeChatID(wechatID);
            StringBuilder sb = new StringBuilder("{\"code\":\"200\",\"content\":[");
            if (cardOrderList.size() > 0) {
                for (card_order order : cardOrderList) {
                    sb.append("{");
                    sb.append("\"id\":\"" + order.getId() + "\",");
                    sb.append("\"card_number\":\"" + order.getCard_number() + "\"},");
                }
            }
            if (sb.toString().contains("card_number")) {
                sb = sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("]}");
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

    @Override
    public String insertOrderWeChat(String Card_number, String Address, String Card_name, String Company_name, String Phone, String Taocan_id, String Taocan_name, String Username, String Wechatid) {
        logger.info("insertOrderWeChat接口接收参数:Card_number=" + Card_number + ",Address=" + Address + ",Card_name=" + Card_name + ",Company_name=" + Company_name + ",Phone=" + Phone + ",Taocan_id=" + Taocan_id + ",Taocan_name=" + Taocan_name + ",Username=" + Username + ",Wechatid=" + Wechatid);
        try {
            int nums = cardOrderRepository.getNumsByNumber(Card_number,Company_name);
            if (nums < 1) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = new Date();

                int i = cardOrderRepository.saveWeChatOrder(Address.trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"",""), Card_name, Card_number, Company_name, Phone, Integer.parseInt(Taocan_id), Taocan_name, Username.trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"",""), Wechatid,sdf.format(d));

                if (i == 1){
                    int companyId = cardCompanyRepository.getCompanyId(Company_name);
                    cardNumberRepository.useCardNumber(Card_number,companyId);
                    return  "{\"code\":\"200\",\"content\":\"您已兑换成功\"}";
                }else {
                    return "{\"code\":\"203\",\"content\":\"兑换失败，请联系工作人员\"}";
                }
            }
            return "{\"code\":\"202\",\"content\":\"不可重复提交订单\"}";
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

    @Override
    public String getOrderInfoById(Integer orderId) {
        logger.info("getOrderInfoById接口接收参数:orderId=" + orderId);
        try {
            card_order cardOrder = cardOrderRepository.getOrderById(orderId);
            StringBuilder sb = new StringBuilder("{\"code\":\"200\",\"content\":{");
            if (cardOrder != null) {

                sb.append("\"card_name\":\"" + cardOrder.getCard_name().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                sb.append("\"taocan_name\":\"" + cardOrder.getTaocan_name().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                sb.append("\"card_number\":\"" + cardOrder.getCard_number().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                sb.append("\"company_name\":\"" + cardOrder.getCompany_name().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                sb.append("\"express_no\":\"" + cardOrder.getExpress_no().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\"");
            }
            sb.append("}}");
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }


}

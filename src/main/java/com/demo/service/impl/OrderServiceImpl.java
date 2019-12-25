package com.demo.service.impl;

import com.demo.bean.card_order;
import com.demo.dao.CardOrderRepository;
import com.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CardOrderRepository cardOrderRepository;

    @Override
    public String getOrderList(String startTime, String endTime, String wechatID, String username, String phone, String address, String taocan_id, String taocan_name, String card_name, String order_state, String express, String express_no, String logistics, String company_name, String card_number, String pageIndex, String pageSize) {
        try {
            int intPageIndex;
            int intPageSize;
            if (pageSize == null || "".equals(pageSize)) {
                intPageSize = 10;
            } else {
                intPageSize = Integer.parseInt(pageSize);
            }
            if ("1".equals(pageIndex) || "".equals(pageIndex) || pageIndex == null) {
                intPageIndex = 0;
            } else {
                intPageIndex = (Integer.parseInt(pageIndex) - 1) * intPageSize;
            }
            List<card_order> orderList = cardOrderRepository.selectOrder(startTime, endTime, wechatID, username, phone, address, taocan_id, taocan_name, card_name, order_state, express, express_no, logistics,company_name,card_number, intPageIndex, intPageSize);
            int orderSize = cardOrderRepository.selectOrderSize(startTime, endTime, wechatID, username, phone, address, taocan_id, taocan_name, card_name, order_state, express, express_no, logistics,company_name,card_number);
            StringBuilder sb = new StringBuilder("{\"code\":\"200\",\"size\":\"" + orderSize + "\",\"content\":[");
            if (orderList.size() > 0) {
                for (card_order order : orderList) {
                    sb.append("{");
                    sb.append("\"orderId\":\"" + order.getId() + "\",");
                    sb.append("\"wechatId\":\"" + order.getWechatid() + "\",");
                    sb.append("\"username\":\"" + order.getUsername() + "\",");
                    sb.append("\"phone\":\"" + order.getPhone() + "\",");
                    sb.append("\"address\":\"" + order.getAddress() + "\",");
                    sb.append("\"taocanName\":\"" + order.getTaocan_name() + "\",");
                    sb.append("\"cardType\":\"" + order.getCard_name() + "\",");
                    sb.append("\"companyName\":\"" + order.getCompany_name() + "\",");
                    sb.append("\"cardNumber\":\"" + order.getCard_number() + "\",");
                    sb.append("\"orderState\":\"" + order.getOrder_state() + "\",");
                    sb.append("\"express\":\"" + order.getExpress() + "\",");
                    sb.append("\"expressNo\":\"" + order.getExpress_no() + "\",");
                    sb.append("\"logistics\":\"" + order.getLogistics() + "\"},");
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
    public String updateOrder(String orderId, String wechatId, String username, String phone, String address, String taocanName, String cardType, String companyName, String cardNumber, String orderState, String express, String expressNo, String logistics) {
        try {
            Timestamp now = new Timestamp(new Date().getTime());
            card_order cardOrder = cardOrderRepository.getOne(Integer.valueOf(orderId));
            if (wechatId!= null && !"".equals(wechatId)){
                cardOrder.setWechatid(wechatId);
            }
            if (username!= null && !"".equals(username)){
                cardOrder.setUsername(username);
            }
            if (phone != null && !"".equals(phone)){
                cardOrder.setPhone(phone);
            }
            if (address != null && !"".equals(address)){
                cardOrder.setAddress(address);
            }
            if (taocanName != null && !"".equals(taocanName)){
                cardOrder.setTaocan_name(taocanName);
            }
            if (cardType != null && !"".equals(cardType)){
                cardOrder.setCard_name(cardType);
            }
            if (companyName != null && !"".equals(companyName)){
                cardOrder.setCompany_name(companyName);
            }
            if (cardNumber != null && !"".equals(cardNumber)){
                cardOrder.setCard_number(cardNumber);
            }
            if (orderState != null && !"".equals(orderState)){
                cardOrder.setOrder_state(Integer.parseInt(orderState));
            }
            if (express != null && !"".equals(express)){
                cardOrder.setExpress(express);
            }
            if (expressNo != null && !"".equals(expressNo)){
                cardOrder.setExpress_no(expressNo);
            }
            if (logistics != null && !"".equals(logistics)){
                cardOrder.setLogistics(logistics);
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
}

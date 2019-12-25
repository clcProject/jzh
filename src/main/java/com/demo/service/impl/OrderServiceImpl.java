package com.demo.service.impl;

import com.demo.bean.card_order;
import com.demo.dao.CardOrderRepository;
import com.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private CardOrderRepository cardOrderRepository;

    @Override
    public String insertOrderWeChat(card_order cardOrder) {
        try {
            cardOrderRepository.save(cardOrder);
            return "{\"code\":\"200\",\"content\":\"添加成功\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

    @Override
    public String getOrderInfoById(Integer orderId) {
        try {
            card_order cardOrder = cardOrderRepository.getOne(orderId);
            StringBuilder sb = new StringBuilder("{\"code\":\"200\",\"content\":[");
            if (cardOrder != null ) {

                sb.append("\"card_name\":\"" + cardOrder.getCard_name() + "\",");
                sb.append("\"taocan_name\":\"" + cardOrder.getTaocan_name()+ "\",");
                sb.append("\"card_number\":\"" + cardOrder.getCard_number() + "\",");
                sb.append("\"company_name\":\"" + cardOrder.getTaocan_name() + "\"},");
                sb.append("\"express_no\":\"" + cardOrder.getExpress_no() );
            }
            sb.append("]}");
            return sb.toString();
        } catch (
                NumberFormatException e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

    @Override
    public String getOrderInfoByWeChatID(String wechatID) {
        try {
            List<card_order> cardOrderList = cardOrderRepository.getOrderInfoByWeChatID(wechatID);
            StringBuilder sb = new StringBuilder("{\"code\":\"200\",\"content\":[");
            if (cardOrderList.size() > 0) {
                for (card_order order : cardOrderList) {
                    sb.append("{");
                    sb.append("\"id\":\"" + order.getId() + "\",");
                    sb.append("\"card_number\":\"" + order.getCard_number() + "\"},");
                }
                if (sb.toString().contains("taocanName")) {
                    sb = sb.deleteCharAt(sb.length() - 1);
                }
            }
            sb.append("]}");
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }
}

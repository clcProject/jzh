package com.demo.service;

import com.demo.bean.card_order;

public interface OrderService {

    
    String insertOrderWeChat(card_order cardOrder);

    String getOrderInfoById(Integer orderId);

    String getOrderInfoByWeChatID(String wechatID);


    String getOrderList(String startTime, String endTime, String wechatID, String username, String phone, String address, String taocan_id, String taocan_name, String card_name, String order_state, String express, String express_no, String logistics, String company_name, String card_number, String pageIndex, String pageSize);

    String updateOrder(String orderId, String wechatId, String username, String phone, String address, String taocanName, String cardType, String companyName, String cardNumber, String orderState, String express, String expressNo, String logistics);

    String deleteOrder(String orderId);

}

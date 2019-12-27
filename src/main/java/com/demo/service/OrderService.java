package com.demo.service;

import com.demo.bean.card_order;

public interface OrderService {


    String insertOrderWeChat(String Card_number, String Address, String Card_name, String Company_name, String Phone, String Taocan_id, String Taocan_name, String Username, String Wechatid);

    String getOrderInfoById(Integer orderId);

    String getOrderInfoByWeChatID(String wechatID);


    String getOrderList(String startTime, String endTime, String wechatID, String username, String phone, String address, String taocan_id, String taocan_name, String card_name, String order_state, String express, String express_no, String logistics, String company_name, String card_number, String pageIndex, String pageSize);

    String updateOrder(String orderId, String wechatId, String username, String phone, String address, String taocanName, String cardType, String companyName, String cardNumber, String orderState, String express, String expressNo, String logistics);

    String deleteOrder(String orderId);

}

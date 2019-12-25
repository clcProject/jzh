package com.demo.service;

import com.demo.bean.card_order;

public interface OrderService {
    
    String insertOrderWeChat(card_order cardOrder);

    String getOrderInfoById(Integer orderId);

    String getOrderInfoByWeChatID(String wechatID);
}

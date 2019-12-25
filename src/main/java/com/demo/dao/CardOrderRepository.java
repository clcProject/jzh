package com.demo.dao;

import com.demo.bean.card_order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardOrderRepository extends JpaRepository<card_order, Integer> {
    @Query(value = "SELECT id,card_number FROM card_order WHERE wechatID = ?1 AND isfalg=0", nativeQuery =
            true)
    List<card_order> getOrderInfoByWeChatID(String wechatID);

}

package com.demo.dao;

import com.demo.bean.card_order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardOrderRepository extends JpaRepository<card_order,Integer> {
}

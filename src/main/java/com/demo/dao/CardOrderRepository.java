package com.demo.dao;

import com.demo.bean.card_order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CardOrderRepository extends JpaRepository<card_order, Integer> {
    @Query(value = "SELECT * FROM card_order WHERE wechatID = ?1 AND isfalg=0", nativeQuery =
            true)
    List<card_order> getOrderInfoByWeChatID(String wechatID);


    @Query(value = "SELECT * from card_order t where if(?1 !='',t.createdate > ?1,1=1) and if(?2 !='',t.createdate < ?2,1=1) AND if(?3 !='',t.wechatID =?3,1=1) AND if(?4 !='',t.username = ?4,1=1) AND if(?5 !='',t.phone = ?5,1=1) AND if(?6 !='',t.address = ?6,1=1) AND if(?7 !='',t.taocan_id = ?7,1=1) AND if(?8 !='',t.taocan_name = ?8,1=1) AND if(?9 !='',t.card_name = ?9,1=1) AND if(?10 !='',t.order_state = ?10,1=1) AND if(?11 !='',t.express = ?11,1=1) AND if(?12 !='',t.express_no = ?12,1=1) AND if(?13 !='',t.logistics = ?13,1=1) AND if(?14 !='',t.company_name = ?14,1=1) AND if(?15 !='',t.card_number = ?15,1=1) and isfalg=0 order by id desc LIMIT ?16,?17", nativeQuery = true)
    List<card_order> selectOrder(String startTime, String endTime, String wechatID, String username, String phone, String address, String taocan_id, String taocan_name, String card_name, String order_state, String express, String express_no, String logistics,String company_name,String card_number, int intPageIndex, int intPageSize);

    @Query(value = "SELECT count(0) from card_order t where if(?1 !='',t.createdate > ?1,1=1) and if(?2 !='',t.createdate < ?2,1=1) AND if(?3 !='',t.wechatID =?3,1=1) AND if(?4 !='',t.username = ?4,1=1) AND if(?5 !='',t.phone = ?5,1=1) AND if(?6 !='',t.address = ?6,1=1) AND if(?7 !='',t.taocan_id = ?7,1=1) AND if(?8 !='',t.taocan_name = ?8,1=1) AND if(?9 !='',t.card_name = ?9,1=1) AND if(?10 !='',t.order_state = ?10,1=1) AND if(?11 !='',t.express = ?11,1=1) AND if(?12 !='',t.express_no = ?12,1=1) AND if(?13 !='',t.logistics = ?13,1=1) AND if(?14 !='',t.company_name = ?14,1=1) AND if(?15 !='',t.card_number = ?15,1=1) and isfalg=0", nativeQuery = true)
    int selectOrderSize(String startTime, String endTime, String wechatID, String username, String phone, String address, String taocan_id, String taocan_name, String card_name, String order_state, String express, String express_no, String logistics,String company_name,String card_number);

    @Query(value = "UPDATE card_order t set t.isfalg = 1 where t.id = ?1",nativeQuery = true)
    @Modifying
    @Transactional
    int deleteOrder(int orderId);

    @Query(value = "SELECT * from card_order t where t.id = ?1",nativeQuery = true)
    card_order getOrderById(int id);

    @Query(value = "SELECT COUNT(0) FROM card_order WHERE card_number= ?1 and company_name = ?2 AND isfalg=0",nativeQuery = true)
    int getNumsByNumber(String card_number,String company_name);

    @Query(value = "INSERT INTO card_order (address,card_name,card_number,company_name,phone,taocan_id,taocan_name,username,wechatid,insertdate) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9,?10)",nativeQuery = true)
    @Modifying
    @Transactional
    int saveWeChatOrder(String cardOrderAddress, String card_name, String card_number, String company_name,
                        String phone, int taocan_id, String taocan_name, String username, String address,String insertdate);
}

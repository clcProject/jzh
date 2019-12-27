package com.demo.dao;

import com.demo.bean.card_number;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CardNumberRepository extends JpaRepository<card_number, Integer> {

    @Query(value = "SELECT t.id,t.cardno,t.`password`,t.companyId,(SELECT b.company from card_company b " +
            "where b.id = t.companyId) as company_name,t.card_type_id,(SELECT c.card_name from card_type c " +
            "where c.id = t.card_type_id) as cardType,t.createdate,CASE WHEN t.card_state = 0 THEN '未激活' " +
            "WHEN t.card_state = 1 THEN '已激活' WHEN t.card_state = 2 THEN '未兑换' WHEN t.card_state = 3 THEN " +
            "'已兑换' END AS '号卡状态' from card_number t where if(?1 !='',t.createdate > ?1,1=1) and if(?2 !=''," +
            "t.createdate < ?2,1=1) AND if(?3 !='',t.cardno like CONCAT('%',?3,'%'),1=1) AND if(?4 !='',t" +
            ".card_type_id = ?4,1=1) AND if(?5 !='',t.companyId = ?5,1=1) AND if(?6 !='',t.card_state = ?6," +
            "1=1) and isfalg=0 LIMIT ?7,?8", nativeQuery = true)
    List<Object[]> selectNumber(String startTime, String endTime, String cardNo, String cardTypeid,
                                String companyid, String cardState, int intPageIndex, int intPageSize);

    @Query(value = "SELECT COUNT(0) from card_number t where if(?1 !='',t.createdate > ?1,1=1) and if(?2 " +
            "!='',t.createdate < ?2,1=1) AND if(?3 !='',t.cardno like CONCAT('%',?3,'%'),1=1) AND if(?4 " +
            "!='',t.card_type_id = ?4,1=1) AND if(?5 !='',t.companyId = ?5,1=1) AND if(?6 !='',t.card_state" +
            " = ?6,1=1) and isfalg=0", nativeQuery = true)
    int selectNumberSize(String startTime, String endTime, String cardNo, String cardTypeid,
                         String companyid, String cardState);

    @Query(value = "SELECT * from card_number t where t.isfalg = 0 AND card_type_id = ?1 AND t.companyId = " +
            "?2 and card_state=?3", nativeQuery = true)
    List<card_number> selectNumberAll(String cardTypeId, String companyId, String cardState);

    @Query(value = "UPDATE card_number t set t.isfalg = 1 where t.id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteCardNumber(int cardId);

    @Query(value = "SELECT IFNULL(SUM(card_state), 0) FROM card_number  WHERE cardno=?1 AND isfalg=0 limit 1", nativeQuery =
            true)
    int IsExist(String cardno);
}

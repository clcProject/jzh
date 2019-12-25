package com.demo.dao;

import com.demo.bean.card_type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CardTypeRepository extends JpaRepository<card_type, Integer> {


    @Query(value = "SELECT t.id,t.card_name,t.company_id,(SELECT b.company from card_company b where b.id = t.company_id) as company_name,t.createdate,t.concent from card_type t where if(?1 !='',t.createdate > ?1,1=1) and if(?2 !='',t.createdate < ?2,1=1) AND if(?3 !='',t.card_name like CONCAT('%',?3,'%'),1=1) AND if(?4 !='',t.company_id = ?4,1=1) and isflag=0 LIMIT ?5,?6", nativeQuery = true)
    List<Object[]> selectType(String startTime, String endTime, String cardName, String companyid, int pageIndex, int pageSize);

    @Query(value = "SELECT COUNT(0) from card_type t where if(?1 !='',t.createdate > ?1,1=1) and if(?2 !='',t.createdate < ?2,1=1) AND if(?3 !='',t.card_name like CONCAT('%',?3,'%'),1=1) AND if(?4 !='',t.company_id = ?4,1=1) and isflag=0", nativeQuery = true)
    int selectTypeSize(String startTime, String endTime, String cardName, String companyid);

    @Query(value = "SELECT * from card_type t where isflag = 0 and company_id = ?1", nativeQuery = true)
    List<card_type> selectTypeAll(String companyid);

    @Query(value = "UPDATE card_type t set t.isflag = 1 where t.id = ?1",nativeQuery = true)
    @Modifying
    @Transactional
    int deleteCardType(int cardTypeid);

}

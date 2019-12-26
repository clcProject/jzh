package com.demo.dao;

import com.demo.bean.card_taocan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CardTaocanRepository extends JpaRepository<card_taocan, Integer> {

    @Query(value = "SELECT t.id,t.taocan_name,t.companyId,(SELECT b.company from card_company b where b.id " +
            "= t.companyId) as company_name,t.card_type_id,(SELECT c.card_name from card_type c where c.id " +
            "= t.card_type_id) as cardType,t.createdate,t.imgurl,t.content from card_taocan t where if(?1 " +
            "!='',t.createdate > ?1,1=1) and if(?2 !='',t.createdate < ?2,1=1) AND if(?3 !='',t.taocan_name" +
            " like CONCAT('%',?3,'%'),1=1) AND if(?4 !='',t.card_type_id = ?4,1=1) AND if(?5 !='',t" +
            ".companyId = ?5,1=1) and isflag=0 LIMIT ?6,?7", nativeQuery = true)
    List<Object[]> selectTaocan(String startTime, String endTime, String taocanName, String cardTypeid,
                                String companyid, int pageIndex, int pageSize);

    @Query(value = "SELECT COUNT(0) from card_taocan t where if(?1 !='',t.createdate > ?1,1=1) and if(?2 " +
            "!='',t.createdate < ?2,1=1) AND if(?3 !='',t.taocan_name like CONCAT('%',?3,'%'),1=1) AND if" +
            "(?4 !='',t.card_type_id = ?4,1=1) AND if(?5 !='',t.companyId = ?5,1=1) and isflag=0",
            nativeQuery = true)
    int selectTaocanSize(String startTime, String endTime, String taocanName, String cardTypeid,
                         String companyid);

    @Query(value = "SELECT * from card_taocan t where t.isflag = 0 AND card_type_id = ?1 AND t.companyId = " +
            "?2", nativeQuery = true)
    List<card_taocan> selectTaocanAll(String cardTypeId, String companyId);

    @Query(value = "UPDATE card_taocan t set t.isflag = 1 where t.id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    int deleteCardTaocan(int taocanId);

    @Query(value = "SELECT t.id,t.imgurl,t.content,(SELECT ct.card_name FROM card_number cn LEFT JOIN " +
            "card_type ct ON cn.card_type_id=ct.id WHERE cn.cardno=?1) as card_name,(SELECT co" +
            ".company FROM card_number cn LEFT JOIN card_company co ON cn.companyId=co.id WHERE cn" +
            ".cardno=?1) as company_name,t.taocan_name FROM card_taocan AS t LEFT JOIN card_number " +
            "AS n ON t.card_type_id = n.card_type_id WHERE n.cardno = ?1 AND n.`password` = " +
            "?2 AND t.isflag=0;", nativeQuery = true)
    List<Object[]> getTaocanByCardInfo(String cardno, String password);

    @Query(value = "SELECT * FROM card_taocan  WHERE id=?1 AND isflag=0", nativeQuery = true)
    card_taocan getTaoCanById(int id);
}

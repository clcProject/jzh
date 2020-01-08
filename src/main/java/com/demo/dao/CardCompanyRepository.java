package com.demo.dao;

import com.demo.bean.card_company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface CardCompanyRepository extends JpaRepository<card_company, Integer> {


    @Query(value = "SELECT * from card_company t where if(?1 !='',t.createdate > ?1,1=1) and if(?2 !='',t.createdate < ?2,1=1) AND if(?3 !='',t.company like CONCAT('%',?3,'%'),1=1) AND if(?4 !='',t.company_address LIKE CONCAT('%',?4,'%'),1=1) and isflag=0 LIMIT ?5,?6", nativeQuery = true)
    List<card_company> selectCompany(String startTime, String endTime, String companyName, String companyAddress, int pageIndex, int pageSize);

    @Query(value = "SELECT id from card_company t where t.company = ?1 ORDER BY id desc LIMIT 1", nativeQuery = true)
    int getCompanyId(String companyName);

    @Query(value = "SELECT COUNT(0) from card_company t where if(?1 !='',t.createdate > ?1,1=1) and if(?2 !='',t.createdate < ?2,1=1) AND if(?3 !='',t.company LIKE CONCAT('%',?3,'%'),1=1) AND if(?4 !='',t.company_address LIKE CONCAT('%',?4,'%'),1=1) and isflag=0", nativeQuery = true)
    int selectCompanySize(String startTime, String endTime, String companyName, String companyAddress);

    @Query(value = "SELECT * from card_company t where isflag = 0", nativeQuery = true)
    List<card_company> selectCompanyAll();

    @Query(value = "UPDATE card_company t set t.isflag = 1 where t.id = ?1",nativeQuery = true)
    @Modifying
    @Transactional
    int deleteCompany(int companyid);

}

package com.demo.service;

public interface CompanyService {

    String getCompanyList(String startTime,String endTime,String companyName,String companyAddress,String pageIndex,String pageSize);

    String getCompanyListAll();

    String deleteCompany(String companyId);

    String addCompany(String companyName,String companyAddress);

    String updateCompany(String companyId,String companyName,String companyAddress);

}

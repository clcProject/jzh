package com.demo.service;

public interface TypeService {

    String getTypeList(String startTime,String endTime,String cardName,String companyId,String pageIndex,String pageSize);

    String getTypeListAll(String companyId);

    String deleteType(String cardTypeId);

    String addType(String cardName,String companyId,String content);

    String updateType(String cardTypeId,String cardName,String companyId,String content);
}

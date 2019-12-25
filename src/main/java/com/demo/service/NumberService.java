package com.demo.service;

public interface NumberService {

    String getNumberList(String startTime, String endTime, String cardNo, String cardTypeid, String companyid, String cardState, String pageIndex, String pageSize);

    String getNumberListAll(String cardTypeId, String companyId, String cardState);

    String deleteNumber(String cardId);

    String addNumber(String cardNo, String password, String cardTypeId, String companyId, String cardState);

    String updateNumber(String cardId, String cardNo, String password, String cardTypeId, String companyId, String cardState);

    String generateCardBatch(String cardTypeId, String companyId, String number);
}

package com.demo.service;

public interface TaocanService {

    String getTaocanList(String startTime, String endTime, String taocanName, String cardTypeid, String companyid, String pageIndex, String pageSize);

    String getTaocanListAll(String cardTypeId,String companyId);

    String deleteTaocan(String taocanId);

    String addTaocan(String taocanName, String cardTypeId, String companyId, String content, String imgBase64);

    String updateTaocan(String taocanId, String taocanName, String cardTypeId, String companyId, String content, String imgBase64);
}

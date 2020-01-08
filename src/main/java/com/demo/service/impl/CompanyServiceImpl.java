package com.demo.service.impl;

import com.demo.bean.card_company;
import com.demo.dao.CardCompanyRepository;
import com.demo.service.CompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CardCompanyRepository cardCompanyRepository;

    private static final Logger logger = LoggerFactory.getLogger(CompanyServiceImpl.class);

    @Override
    public String getCompanyList(String startTime, String endTime, String companyName, String companyAddress, String pageIndex, String pageSize) {

        logger.info("getCompanyList接口接收参数:startTime=" + startTime + ",endTime=" + endTime + ",companyName=" + companyName + ",companyAddress=" + companyAddress + ",pageIndex=" + pageIndex + ",pageSize=" + pageSize);
        try {
            int intPageIndex = 0;
            int intPageSize = 0;
            if (pageSize == null || "".equals(pageSize)) {
                intPageSize = 10;
            } else {
                intPageSize = Integer.parseInt(pageSize);
            }
            if ("1".equals(pageIndex) || "".equals(pageIndex) || pageIndex == null) {
                intPageIndex = 0;
            } else {
                intPageIndex = (Integer.parseInt(pageIndex) - 1) * intPageSize;
            }
            List<card_company> companyList = cardCompanyRepository.selectCompany(startTime, endTime, companyName, companyAddress, intPageIndex, intPageSize);
            int companySize = cardCompanyRepository.selectCompanySize(startTime, endTime, companyName, companyAddress);
            StringBuilder sb = new StringBuilder("{\"code\":\"200\",\"size\":\"" + companySize + "\",\"content\":[");
            if (companyList.size() > 0) {
                for (card_company company : companyList) {
                    sb.append("{");
                    sb.append("\"companyId\":\"" + company.getId() + "\",");
                    sb.append("\"companyName\":\"" + company.getCompany().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"", "") + "\",");
                    sb.append("\"companyAddress\":\"" + company.getCompany_address().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"", "") + "\",");
                    sb.append("\"createDate\":\"" + company.getCreatedate() + "\"},");
                }
                if (sb.toString().contains("companyName")) {
                    sb = sb.deleteCharAt(sb.length() - 1);
                }
            }
            sb.append("]}");
            return sb.toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

    @Override
    public String getCompanyListAll() {
        try {
            List<card_company> companyList = cardCompanyRepository.selectCompanyAll();
            StringBuilder sb = new StringBuilder("{\"code\":\"200\",\"content\":[");
            if (companyList.size() > 0) {
                for (card_company company : companyList) {
                    sb.append("{");
                    sb.append("\"companyId\":\"" + company.getId() + "\",");
                    sb.append("\"companyName\":\"" + company.getCompany().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"", "") + "\"},");
                }
                if (sb.toString().contains("companyName")) {
                    sb = sb.deleteCharAt(sb.length() - 1);
                }
            }
            sb.append("]}");
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

    /*
     *@Author:xjy
     *@Description:删除公司信息
     *@Date:2019/12/23_15:03
     */
    public String deleteCompany(String companyId) {
        logger.info("deleteCompany接口接收参数:companyId=" + companyId);
        try {
            int deleteResult = cardCompanyRepository.deleteCompany(Integer.parseInt(companyId));
            if (deleteResult > 0) {
                return "{\"code\":\"200\",\"content\":\"删除成功\"}";
            } else {
                return "{\"code\":\"201\",\"content\":\"删除失败\"}";
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

    @Override
    public String addCompany(String companyName, String companyAddress) {
        logger.info("addCompany接口接收参数:companyName=" + companyName + ",companyAddress=" + companyAddress);
        try {
            Timestamp now = new Timestamp(new Date().getTime());

            card_company company = new card_company();
            company.setCompany(companyName.trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"", ""));
            company.setCompany_address(companyAddress.trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"", ""));
            company.setCreatedate(now);
            cardCompanyRepository.save(company);
            return "{\"code\":\"200\",\"content\":\"添加成功\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

    @Override
    public String updateCompany(String companyId, String companyName, String companyAddress) {
        logger.info("updateCompany接口接收参数:companyName=" + companyName + ",companyAddress=" + companyAddress + ",companyId=" + companyId);
        try {
            Timestamp now = new Timestamp(new Date().getTime());
            card_company company = cardCompanyRepository.getOne(Integer.valueOf(companyId));
            if (companyName != null && !"".equals(companyName)) {
                company.setCompany(companyName.trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"", ""));
            }
            if (companyAddress != null && !"".equals(companyAddress)) {
                company.setCompany_address(companyAddress.trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"", ""));
            }
            company.setCreatedate(now);
            cardCompanyRepository.save(company);
            return "{\"code\":\"200\",\"content\":\"修改成功\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }


}

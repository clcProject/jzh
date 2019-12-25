package com.demo.service.impl;

import com.demo.bean.card_type;
import com.demo.dao.CardTypeRepository;
import com.demo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service("typeService")
public class TypeServiceImpl implements TypeService {

    @Autowired
    private CardTypeRepository cardTypeRepository;


    @Override
    public String getTypeList(String startTime, String endTime, String cardName, String companyId, String pageIndex, String pageSize) {
        try {
            int intPageIndex;
            int intPageSize;
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
            List<Object[]> cardTypeList = cardTypeRepository.selectType(startTime, endTime, cardName, companyId, intPageIndex, intPageSize);
            int cardTypeSize = cardTypeRepository.selectTypeSize(startTime, endTime, cardName, companyId);
            StringBuilder sb = new StringBuilder("{\"code\":\"200\",\"size\":\"" + cardTypeSize + "\",\"content\":[");
            if (cardTypeList.size() > 0) {
                for (Object[] objects : cardTypeList) {
                    sb.append("{");
                    sb.append("\"cardTypeId\":\"" + objects[0].toString() + "\",");
                    sb.append("\"cardName\":\"" + objects[1].toString() + "\",");
                    sb.append("\"companyId\":\"" + objects[2].toString() + "\",");
                    sb.append("\"companyName\":\"" + objects[3].toString() + "\",");
                    sb.append("\"createDate\":\"" + objects[4].toString() + "\",");
                    sb.append("\"content\":\"" + objects[5].toString() + "\"},");
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
    public String getTypeListAll(String companyId) {
        try {
            List<card_type> cardTypeList = cardTypeRepository.selectTypeAll(companyId);
            StringBuilder sb = new StringBuilder("{\"code\":\"200\",\"content\":[");
            if (cardTypeList.size() > 0) {
                for (card_type card_type : cardTypeList) {
                    sb.append("{");
                    sb.append("\"cardTypeId\":\"" + card_type.getId() + "\",");
                    sb.append("\"cardName\":\"" + card_type.getCard_name() + "\"},");
                }
                if (sb.toString().contains("cardName")) {
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

    @Override
    public String deleteType(String cardTypeId) {
        try {
            int deleteResult = cardTypeRepository.deleteCardType(Integer.parseInt(cardTypeId));
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
    public String addType(String cardName, String companyId, String content) {
        try {
            Timestamp now = new Timestamp(new Date().getTime());

            card_type cardType = new card_type();
            cardType.setCard_name(cardName);
            cardType.setCompany_id(Integer.parseInt(companyId));
            cardType.setConcent(content);
            cardType.setCreatedate(now);
            cardTypeRepository.save(cardType);
            return "{\"code\":\"200\",\"content\":\"添加成功\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

    @Override
    public String updateType(String cardTypeId, String cardName, String companyId, String content) {
        try {
            Timestamp now = new Timestamp(new Date().getTime());
            card_type cardType = cardTypeRepository.getOne(Integer.valueOf(cardTypeId));
            cardType.setCard_name(cardName);
            cardType.setCompany_id(Integer.parseInt(companyId));
            cardType.setConcent(content);
            cardType.setCreatedate(now);
            cardTypeRepository.save(cardType);
            return "{\"code\":\"200\",\"content\":\"修改成功\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }
}

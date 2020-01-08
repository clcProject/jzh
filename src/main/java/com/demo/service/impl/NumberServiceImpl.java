package com.demo.service.impl;

import com.demo.bean.card_number;
import com.demo.dao.CardNumberRepository;
import com.demo.service.NumberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service("numberService")
public class NumberServiceImpl implements NumberService {

    @Autowired
    private CardNumberRepository cardNumberRepository;
    private static final Logger logger = LoggerFactory.getLogger(NumberServiceImpl.class);

    @Override
    public String getNumberList(String startTime, String endTime, String cardNo, String cardTypeid,
                                String companyid, String cardState, String pageIndex, String pageSize) {
        logger.info("getNumberList接口接收参数:startTime=" + startTime + ",endTime=" + endTime + ",cardNo=" + cardNo + ",cardTypeid=" + cardTypeid + ",companyid=" + companyid + ",cardState=" + cardState + ",pageIndex=" + pageIndex + ",pageSize=" + pageSize);

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
            if ("4".equals(cardState) || "-1".equals(cardState)) {
                cardState = "";
            }
            List<Object[]> cardNumberList = cardNumberRepository.selectNumber(startTime, endTime, cardNo,
                    cardTypeid, companyid, cardState, intPageIndex, intPageSize);
            int cardNumberSize = cardNumberRepository.selectNumberSize(startTime, endTime, cardNo,
                    cardTypeid, companyid, cardState);
            StringBuilder sb = new StringBuilder("{\"code\":\"200\",\"size\":\"" + cardNumberSize + "\"," +
                    "\"content\":[");
            if (cardNumberList.size() > 0) {
                for (Object[] objects : cardNumberList) {
                    sb.append("{");
                    sb.append("\"cardId\":\"" + objects[0].toString() + "\",");
                    sb.append("\"cardNo\":\"" + objects[1].toString() + "\",");
                    sb.append("\"password\":\"" + objects[2].toString() + "\",");
                    sb.append("\"companyId\":\"" + objects[3].toString() + "\",");
                    sb.append("\"companyName\":\"" + objects[4].toString() + "\",");
                    sb.append("\"cardTypeId\":\"" + objects[5].toString() + "\",");
                    sb.append("\"cardTypeName\":\"" + objects[6].toString() + "\",");
                    sb.append("\"createdate\":\"" + objects[7].toString() + "\",");
                    sb.append("\"card_state\":\"" + objects[8].toString() + "\"},");
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
    public String getNumberListAll(String cardTypeId, String companyId, String cardState) {
        logger.info("getNumberListAll接口接收参数:cardTypeId=" + cardTypeId + ",companyId=" + companyId + ",cardState=" + cardState);
        try {
            List<card_number> cardNumberList = cardNumberRepository.selectNumberAll(cardTypeId, companyId,
                    cardState);
            StringBuilder sb = new StringBuilder("{\"code\":\"200\",\"content\":[");
            if (cardNumberList.size() > 0) {
                for (card_number cardNumber : cardNumberList) {
                    sb.append("{");
                    sb.append("\"cardId\":\"" + cardNumber.getId() + "\",");
                    sb.append("\"cardNo\":\"" + cardNumber.getCardno() + "\"},");
                }
                if (sb.toString().contains("cardNo")) {
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
    public String deleteNumber(String cardId) {
        logger.info("deleteNumber接口接收参数:cardId=" + cardId);
        try {
            int deleteResult = cardNumberRepository.deleteCardNumber(Integer.parseInt(cardId));
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
    public String addNumber(String cardNo, String password, String cardTypeId, String companyId,
                            String cardState) {
        logger.info("addNumber接口接收参数:cardNo=" + cardNo + ",password=" + password + ",cardTypeId=" + cardTypeId + ",companyId=" + companyId + ",cardState=" + cardState);
        try {
            Timestamp now = new Timestamp(new Date().getTime());
            card_number cardNumber = new card_number();
            cardNumber.setCardno(cardNo);
            cardNumber.setPassword(password);
            cardNumber.setCard_type_id(Integer.parseInt(cardTypeId));
            cardNumber.setCompanyId(Integer.parseInt(companyId));
            cardNumber.setCard_state(Integer.parseInt(cardState));
            cardNumber.setCreatedate(now);
            cardNumberRepository.save(cardNumber);
            return "{\"code\":\"200\",\"content\":\"添加成功\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

    @Override
    public String updateNumber(String cardId, String cardNo, String password, String cardTypeId,
                               String companyId, String cardState) {
        logger.info("addNumber接口接收参数:cardId=" + cardId + ",cardNo=" + cardNo + ",password=" + password + ",cardTypeId=" + cardTypeId + ",companyId=" + companyId + ",cardState=" + cardState);
        try {
            Timestamp now = new Timestamp(new Date().getTime());
            card_number cardNumber = cardNumberRepository.getOne(Integer.valueOf(cardId));
            if (cardNo != null && !"".equals(cardNo)) {
                cardNumber.setCardno(cardNo);
            }
            if (password != null && !"".equals(password)) {
                cardNumber.setPassword(password);
            }
            if (cardTypeId != null && !"".equals(cardTypeId)) {
                cardNumber.setCard_type_id(Integer.parseInt(cardTypeId));
            }
            if (companyId != null && !"".equals(companyId)) {
                cardNumber.setCompanyId(Integer.parseInt(companyId));
            }
            if (cardState != null && !"".equals(cardState) && "-1".equals(cardState)) {
                cardNumber.setCard_state(Integer.parseInt(cardState));
            }
            cardNumber.setCreatedate(now);
            cardNumberRepository.save(cardNumber);
            return "{\"code\":\"200\",\"content\":\"修改成功\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

    @Override
    public String generateCardBatch(String cardTypeId, String companyId, String number) {
        logger.info("generateCardBatch接口接收参数:cardTypeId=" + cardTypeId + ",companyId=" + companyId + ",number=" + number);

        try {
            int num = Integer.parseInt(number);

            for (int i = 0; i < num; i++) {
                Timestamp now = new Timestamp(new Date().getTime());
                long currentTime = System.currentTimeMillis();
                card_number cardNumber = new card_number();
                Random rand = new Random();
                String cardNo = "ZZ" + String.valueOf(currentTime + (rand.nextInt(899) + 100));
                // 卡+unix时间戳+3位随机数";//
                cardNumber.setCardno(cardNo);
                cardNumber.setPassword(generateShortUUID());
                cardNumber.setCard_type_id(Integer.parseInt(cardTypeId));
                cardNumber.setCompanyId(Integer.parseInt(companyId));
                cardNumber.setCard_state(2);
                cardNumber.setCreatedate(now);
                cardNumberRepository.save(cardNumber);
            }

            return "{\"code\":\"200\",\"content\":\"" + number + "张号卡添加成功\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

    @Override
    public String IsExist(String cardno) {
        logger.info("IsExist接口接收参数:cardno=" + cardno);
        try {
            int IsExistResult = cardNumberRepository.IsExist(cardno);
            System.out.println(IsExistResult);
            if (IsExistResult > 0) {
                return "{\"code\":\"200\",\"content\":\"未兑换\"}";

            }else {
                return "{\"code\":\"202\",\"content\":\"卡号不存在或已使用\"}";
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};


    public static String generateShortUUID() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }
}

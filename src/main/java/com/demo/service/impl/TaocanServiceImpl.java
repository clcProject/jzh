package com.demo.service.impl;

import com.demo.bean.card_taocan;
import com.demo.dao.CardTaocanRepository;
import com.demo.service.TaocanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service("taocanService")
public class TaocanServiceImpl implements TaocanService {

    @Autowired
    private CardTaocanRepository cardTaocanRepository;

    @Override
    public String getTaocanList(String startTime, String endTime, String taocanName, String cardTypeid,
                                String companyid, String pageIndex, String pageSize) {
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
            List<Object[]> cardTaocanList = cardTaocanRepository.selectTaocan(startTime, endTime,
                    taocanName, cardTypeid, companyid, intPageIndex, intPageSize);
            int cardTaocanSize = cardTaocanRepository.selectTaocanSize(startTime, endTime, taocanName,
                    cardTypeid, companyid);
            StringBuilder sb = new StringBuilder("{\"code\":\"200\",\"size\":\"" + cardTaocanSize + "\"," +
                    "\"content\":[");
            if (cardTaocanList.size() > 0) {
                for (Object[] objects : cardTaocanList) {
                    sb.append("{");
                    sb.append("\"taocanId\":\"" + objects[0].toString() + "\",");
                    sb.append("\"taocanName\":\"" + objects[1].toString() + "\",");
                    sb.append("\"companyId\":\"" + objects[2].toString() + "\",");
                    sb.append("\"companyName\":\"" + objects[3].toString() + "\",");
                    sb.append("\"cardTypeId\":\"" + objects[4].toString() + "\",");
                    sb.append("\"cardTypeName\":\"" + objects[5].toString() + "\",");
                    sb.append("\"createdate\":\"" + objects[6].toString() + "\",");
                    sb.append("\"imgUrl\":\"" + objects[7].toString() + "\",");
                    sb.append("\"content\":\"" + objects[8].toString() + "\"},");
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
    public String getTaocanListAll(String cardTypeId, String companyId) {
        try {
            List<card_taocan> cardTaocanList = cardTaocanRepository.selectTaocanAll(cardTypeId, companyId);
            StringBuilder sb = new StringBuilder("{\"code\":\"200\",\"content\":[");
            if (cardTaocanList.size() > 0) {
                for (card_taocan taocan : cardTaocanList) {
                    sb.append("{");
                    sb.append("\"taocanId\":\"" + taocan.getId() + "\",");
                    sb.append("\"taocanName\":\"" + taocan.getTaocan_name() + "\"},");
                }
                if (sb.toString().contains("taocanName")) {
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
    public String deleteTaocan(String taocanId) {
        try {
            int deleteResult = cardTaocanRepository.deleteCardTaocan(Integer.parseInt(taocanId));
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
    public String addTaocan(String taocanName, String cardTypeId, String companyId, String content,
                            String imgBase64) {
        try {
            Timestamp now = new Timestamp(new Date().getTime());
            String FilePath = "";

            if (imgBase64 != null && !"".equals(imgBase64)) {
                imgBase64 = imgBase64.trim();
                imgBase64 = imgBase64.replaceAll("\n", "");
                imgBase64 = imgBase64.replaceAll("\r", "");
                imgBase64 = imgBase64.replaceAll("\t", "");
                imgBase64 = imgBase64.replaceAll(" ", "+");

                if (imgBase64 == null) { //图像数据为空

                    return "{\"code\": 201," +
                            "\"Content\": \"图像数据为空\"}";
                }
                BASE64Decoder decoder = new BASE64Decoder();

                //Base64解码
                byte[] b = decoder.decodeBuffer(imgBase64);
                for (int i = 0; i < b.length; ++i) {
                    if (b[i] < 0) {//调整异常数据
                        b[i] += 256;
                    }
                }
                long currentTime = System.currentTimeMillis();
                //生成jpeg图片
                String photoName = taocanName + "_" + cardTypeId + "_" + companyId;
//            String imgFilePath = "/data/usr/local/tomcat/apache-tomcat-7.0.82/webapps";//服务器
                String imgFilePath = "E:/";//本地测试
                FilePath = "/exchangeImg/" + photoName + ".jpg";//新生成的图片

                OutputStream out = new FileOutputStream(imgFilePath + FilePath);
                out.write(b);
                out.flush();
                out.close();

            }

            card_taocan cardTaocan = new card_taocan();
            cardTaocan.setTaocan_name(taocanName);
            cardTaocan.setCard_type_id(Integer.parseInt(cardTypeId));
            cardTaocan.setCompanyId(Integer.parseInt(companyId));
            cardTaocan.setContent(content);
            cardTaocan.setImgurl(FilePath);
            cardTaocan.setCreatedate(now);
            cardTaocanRepository.save(cardTaocan);
            return "{\"code\":\"200\",\"content\":\"添加成功\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

    @Override
    public String updateTaocan(String taocanId, String taocanName, String cardTypeId, String companyId,
                               String content, String imgBase64) {
        try {
            Timestamp now = new Timestamp(new Date().getTime());
            card_taocan cardTaocan = cardTaocanRepository.getOne(Integer.valueOf(taocanId));

            if (imgBase64 != null && !"".equals(imgBase64)) {
                imgBase64 = imgBase64.trim();
                imgBase64 = imgBase64.replaceAll("\n", "");
                imgBase64 = imgBase64.replaceAll("\r", "");
                imgBase64 = imgBase64.replaceAll("\t", "");
                imgBase64 = imgBase64.replaceAll(" ", "+");

                if (imgBase64 == null) { //图像数据为空

                    return "{\"code\": 201," +
                            "\"Content\": \"图像数据为空\"}";
                }
                BASE64Decoder decoder = new BASE64Decoder();

                //Base64解码
                byte[] b = decoder.decodeBuffer(imgBase64);
                for (int i = 0; i < b.length; ++i) {
                    if (b[i] < 0) {//调整异常数据
                        b[i] += 256;
                    }
                }
                long currentTime = System.currentTimeMillis();
                //生成jpeg图片
                String photoName = taocanName + "_" + cardTypeId + "_" + companyId;
//            String imgFilePath = "/data/usr/local/tomcat/apache-tomcat-7.0.82/webapps";//服务器
                String imgFilePath = "E:/";//本地测试
                String FilePath = "/exchangeImg/" + photoName + ".jpg";//新生成的图片

                OutputStream out = new FileOutputStream(imgFilePath + FilePath);
                out.write(b);
                out.flush();
                out.close();
                cardTaocan.setImgurl(FilePath);
            }

            if (taocanName != null && !"".equals(taocanName)) {
                cardTaocan.setTaocan_name(taocanName);
            }
            if (cardTypeId != null && !"".equals(cardTypeId)) {
                cardTaocan.setCard_type_id(Integer.parseInt(cardTypeId));
            }
            if (companyId != null && !"".equals(companyId)) {
                cardTaocan.setCompanyId(Integer.parseInt(companyId));
            }
            if (content != null && !"".equals(content)) {
                cardTaocan.setContent(content);
            }
            cardTaocan.setCreatedate(now);
            cardTaocanRepository.save(cardTaocan);
            return "{\"code\":\"200\",\"content\":\"修改成功\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

    @Override
    public String getTaoCanById(Integer taocanID) {
        try {
            card_taocan card_taocan = cardTaocanRepository.getTaoCanById(taocanID);
            StringBuilder sb = new StringBuilder("{\"code\":\"200\",\"content\":[");
            if (card_taocan != null ) {

                sb.append("\"taocanId\":\"" + card_taocan.getId() + "\",");
                sb.append("\"imgurl\":\"" + card_taocan.getImgurl() + "\",");
                sb.append("\"content\":\"" + card_taocan.getContent() + "\",");
                sb.append("\"taocan_name\":\"" + card_taocan.getTaocan_name() + "\"},");
            }
            sb.append("]}");
            return sb.toString();
        } catch (
                NumberFormatException e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

    @Override
    public String getTaocanByCardInfo(String cardno, String password) {
        try {
            List<Object[]> taoCanList = cardTaocanRepository.getTaocanByCardInfo(cardno, password);
            StringBuilder sb = new StringBuilder("{\"code\":\"200\",\"content\":[");
            if (taoCanList.size() > 0) {
                for (Object[] objects : taoCanList) {
                    sb.append("{");
                    sb.append("\"taocanId\":\"" + objects[0].toString() + "\",");
                    sb.append("\"imgurl\":\"" + objects[1].toString() + "\",");
                    sb.append("\"content\":\"" + objects[2].toString() + "\",");
                    sb.append("\"taocan_name\":\"" + objects[3].toString() + "\"},");
                }
            }
            sb.append("]}");
            return sb.toString();
        } catch (
                NumberFormatException e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

}

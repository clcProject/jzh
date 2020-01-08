package com.demo.service.impl;

import com.demo.bean.card_taocan;
import com.demo.dao.CardTaocanRepository;
import com.demo.service.TaocanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(TaocanServiceImpl.class);
    @Override
    public String getTaocanList(String startTime, String endTime, String taocanName, String cardTypeid,
                                String companyid, String pageIndex, String pageSize) {
        logger.info("getTaocanList接口接收参数:startTime=" + startTime + ",endTime="+endTime+",taocanName="+taocanName+",cardTypeid="+cardTypeid+",companyid="+companyid+",pageIndex="+pageIndex+",pageSize="+pageSize);

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
                    sb.append("\"taocanName\":\"" + objects[1].toString().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                    sb.append("\"companyId\":\"" + objects[2].toString() + "\",");
                    sb.append("\"companyName\":\"" + objects[3].toString().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                    sb.append("\"cardTypeId\":\"" + objects[4].toString() + "\",");
                    sb.append("\"cardTypeName\":\"" + objects[5].toString().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                    sb.append("\"createdate\":\"" + objects[6].toString() + "\",");
                    sb.append("\"imgUrl\":\"" + objects[7].toString().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                    sb.append("\"content\":\"" + objects[8].toString().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\"},");
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
        logger.info("getTaocanListAll接口接收参数:cardTypeId=" + cardTypeId + ",companyId="+companyId);

        try {
            List<card_taocan> cardTaocanList = cardTaocanRepository.selectTaocanAll(cardTypeId, companyId);
            StringBuilder sb = new StringBuilder("{\"code\":\"200\",\"content\":[");
            if (cardTaocanList.size() > 0) {
                for (card_taocan taocan : cardTaocanList) {
                    sb.append("{");
                    sb.append("\"taocanId\":\"" + taocan.getId() + "\",");
                    sb.append("\"taocanName\":\"" + taocan.getTaocan_name().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\"},");
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
        logger.info("deleteTaocan接口接收参数:taocanId=" + taocanId);

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
        logger.info("addTaocan接口接收参数:taocanName=" + taocanName+",cardTypeId="+cardTypeId+",companyId="+companyId+",content="+content);
        try {
            Timestamp now = new Timestamp(new Date().getTime());
            String FilePath = "/exchangeImg/default.jpg";

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
                String photoName = currentTime + "_" + cardTypeId + "_" + companyId;
            String imgFilePath = "/usr/local/tomcat/apache-tomcat-7.0.82/webapps/";//服务器
//                String imgFilePath = "E:/";//本地测试
                FilePath = "/exchangeImg/" + photoName + ".jpg";//新生成的图片

                OutputStream out = new FileOutputStream(imgFilePath + FilePath);
                out.write(b);
                out.flush();
                out.close();

            }

            card_taocan cardTaocan = new card_taocan();
            cardTaocan.setTaocan_name(taocanName.trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"",""));
            cardTaocan.setCard_type_id(Integer.parseInt(cardTypeId));
            cardTaocan.setCompanyId(Integer.parseInt(companyId));
            cardTaocan.setContent(content.trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"",""));

            cardTaocan.setImgurl("http://47.95.5.141:8080"+FilePath);
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
        logger.info("updateTaocan接口接收参数:taocanId="+taocanId+",taocanName=" + taocanName+",cardTypeId="+cardTypeId+",companyId="+companyId+",content="+content);

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
                String photoName = currentTime + "_" + cardTypeId + "_" + companyId;
                String imgFilePath = "/usr/local/tomcat/apache-tomcat-7.0.82/webapps/";//服务器
//                String imgFilePath = "E:/";//本地测试
                String FilePath = "/exchangeImg/" + photoName + ".jpg";//新生成的图片

                OutputStream out = new FileOutputStream(imgFilePath + FilePath);
                out.write(b);
                out.flush();
                out.close();
                cardTaocan.setImgurl("http://47.95.5.141:8080"+FilePath);
            }

            if (taocanName != null && !"".equals(taocanName)) {
                cardTaocan.setTaocan_name(taocanName.trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"",""));
            }
            if (cardTypeId != null && !"".equals(cardTypeId)) {
                cardTaocan.setCard_type_id(Integer.parseInt(cardTypeId));
            }
            if (companyId != null && !"".equals(companyId)) {
                cardTaocan.setCompanyId(Integer.parseInt(companyId));
            }
            if (content != null && !"".equals(content)) {
                cardTaocan.setContent(content.trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"",""));
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
        logger.info("getTaoCanById接口接收参数:taocanID="+taocanID);

        try {
            card_taocan card_taocan = cardTaocanRepository.getTaoCanById(taocanID);
            StringBuilder sb = new StringBuilder("{\"code\":\"200\",\"content\":{");
            if (card_taocan != null ) {

                sb.append("\"taocanId\":\"" + card_taocan.getId() + "\",");
                sb.append("\"imgurl\":\"" + card_taocan.getImgurl().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                sb.append("\"content\":\"" + card_taocan.getContent().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                sb.append("\"taocan_name\":\"" + card_taocan.getTaocan_name().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\"");
            }
            sb.append("}}");
            return sb.toString();
        } catch (
                NumberFormatException e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

    @Override
    public String getTaocanByCardInfo(String cardno, String password) {
        logger.info("getTaocanByCardInfo接口接收参数:cardno="+cardno+",password="+password);

        try {
            List<Object[]> taoCanList = cardTaocanRepository.getTaocanByCardInfo(cardno, password);
            StringBuilder sb = new StringBuilder("{\"code\":\"200\",\"content\":[");
            if (taoCanList.size() > 0) {
                for (Object[] objects : taoCanList) {
                    sb.append("{");
                    sb.append("\"taocanId\":\"" + objects[0].toString() + "\",");
                    sb.append("\"imgurl\":\"" + objects[1].toString().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                    sb.append("\"content\":\"" + objects[2].toString().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                    sb.append("\"card_name\":\"" + objects[3].toString().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                    sb.append("\"company_name\":\"" + objects[4].toString().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\",");
                    sb.append("\"taocan_name\":\"" + objects[5].toString().trim().replaceAll("\t", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\"","") + "\"},");
                }
            }
            if (sb.toString().contains("taocan_name")){
                sb = sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("]}");
            return sb.toString();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "{\"code\":\"201\",\"content\":\"系统异常\"}";
        }
    }

}

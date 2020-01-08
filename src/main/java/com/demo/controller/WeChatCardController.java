package com.demo.controller;

import com.demo.bean.card_order;
import com.demo.service.NumberService;
import com.demo.service.OrderService;
import com.demo.service.TaocanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * @ClassName WeChatCardController
 * @Description 微信小程序专用接口
 * @Author clc
 * @Date 2019/12/2512:16
 **/
@RestController
@RequestMapping("/wechat/card")
@Component
public class WeChatCardController {

    @Autowired
    private NumberService numberService;
    @Autowired
    private TaocanService taocanService;
    @Autowired
    private OrderService orderService;

    /*
     * @Author clc
     * @Description //验证卡号是否兑换
     * @Date 12:19 2019/12/25
     * @Param [request, response]
     * @return java.lang.String
     **/
    @RequestMapping(value = "/isExist", method = {RequestMethod.GET, RequestMethod.POST})
    public String isExist(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        String cardno=request.getParameter("cardno");

        return numberService.IsExist(cardno);
    }

    /*
     *
     * @Author clc
     * @Description //根据卡号查询套餐列表
     * @Date 12:20 2019/12/25
     * @Param [request, response]
     * @return java.lang.String
     **/
    @RequestMapping(value = "/getTaocanInfoByCard", method = {RequestMethod.GET, RequestMethod.POST})
    public String getTaocanInfoByCard(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        String cardno=request.getParameter("cardno");
        String password=request.getParameter("password");

        return taocanService.getTaocanByCardInfo(cardno,password);
    }

    /*
     *
     * @Author clc
     * @Description //获取套餐详情
     * @Date 12:28 2019/12/25
     * @Param [request, response]
     * @return java.lang.String
     **/
    @RequestMapping(value = "/getTaocanById", method = {RequestMethod.GET, RequestMethod.POST})
    public String getTaocanById(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        Integer taocanID= Integer.valueOf(request.getParameter("taocanId"));

        return taocanService.getTaoCanById(taocanID);
    }

    /*
     *
     * @Author clc
     * @Description //根据小程序用户ID获取兑换记录列表
     * @Date 12:29 2019/12/25
     * @Param [request, response]
     * @return java.lang.String
     **/
    @RequestMapping(value = "/getOrderByWechatID", method = {RequestMethod.GET, RequestMethod.POST})
    public String getOrderByWechatID(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        String wechatID=request.getParameter("wechatID");
        return orderService.getOrderInfoByWeChatID(wechatID);
    }

    /*
     *
     * @Author clc
     * @Description //获取兑换记录详情
     * @Date 12:31 2019/12/25
     * @Param [request, response]
     * @return java.lang.String
     **/
    @RequestMapping(value = "/getOrderInfoByOrderID", method = {RequestMethod.GET, RequestMethod.POST})
    public String getOrderInfoByOrderID(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        Integer orderId= Integer.valueOf(request.getParameter("id"));
        return orderService.getOrderInfoById(orderId);
    }

    /*
     *
     * @Author clc
     * @Description //提交兑换订单
     * @Date 12:33 2019/12/25
     * @Param [request, response]
     * @return java.lang.String
     **/
    @RequestMapping(value = "/addOrderByWeChat", method = {RequestMethod.GET, RequestMethod.POST})
    public String addOrderByWeChat(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

//        String Wechatid="NO";
//        String Address=request.getParameter("address");
//        String Card_name=request.getParameter("card_name");
//        String Company_name=request.getParameter("company_name");
//        String Phone=request.getParameter("phone");
//        String Taocan_id=request.getParameter("taocan_id");
//        String Taocan_name=request.getParameter("taocan_name");
//        String Username=request.getParameter("username");
//        String Card_number=request.getParameter("wechatid");
        String Card_number=request.getParameter("card_number");
        String Address=request.getParameter("address");
        String Card_name=request.getParameter("card_name");
        String Company_name=request.getParameter("company_name");
        String Phone=request.getParameter("phone");
        String Taocan_id=request.getParameter("taocan_id");
        String Taocan_name=request.getParameter("taocan_name");
        String Username=request.getParameter("username");
        String Wechatid=request.getParameter("wechatid");

        return orderService.insertOrderWeChat(Card_number,Address,Card_name,Company_name,Phone,Taocan_id,Taocan_name,Username,Wechatid);
    }

}
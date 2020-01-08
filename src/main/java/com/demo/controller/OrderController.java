package com.demo.controller;

import com.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/order")
@Component
public class OrderController {

    @Autowired
    private OrderService orderService;

    /*
     *@Author:xjy
     *@Description:获取订单列表
     *@Date:2019/12/25_11:30
     */
    @RequestMapping(value = "/getOrderList", method = {RequestMethod.GET, RequestMethod.POST})
    public String getOrderList(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String wechatID = request.getParameter("wechatID");
        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String taocan_id = request.getParameter("taocan_id");
        String taocan_name = request.getParameter("taocan_name");
        String card_name = request.getParameter("card_name");
        String company_name = request.getParameter("company_name");
        String card_number = request.getParameter("card_number");
        String order_state = request.getParameter("order_state");
        String express = request.getParameter("express");
        String express_no = request.getParameter("express_no");
        String logistics = request.getParameter("logistics");
        String pageIndex = request.getParameter("pageIndex");
        String pageSize = request.getParameter("pageSize");

        return orderService.getOrderList(startTime, endTime, wechatID, username, phone, address, taocan_id, taocan_name, card_name, order_state, express, express_no, logistics, company_name, card_number, pageIndex, pageSize);
    }

    /*
     *@Author:xjy
     *@Description:修改订单信息
     *@Date:2019/12/25_14:56
     */
    @RequestMapping(value = "/updateOrder", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateOrder(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        String orderId = request.getParameter("orderId");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String orderState = request.getParameter("orderState");
        String express = request.getParameter("express");
        String expressNo = request.getParameter("expressNo");

        return orderService.updateOrder(orderId,phone, address,orderState,express,expressNo);
    }

    /*
     *@Author:xjy
     *@Description:删除订单
     *@Date:2019/12/25_15:40
     */
    @RequestMapping(value = "/deleteOrder", method = {RequestMethod.GET, RequestMethod.POST})
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        String orderId = request.getParameter("orderId");

        return orderService.deleteOrder(orderId);
    }

    /*
     *@Author:xjy
     *@Description:登录
     *@Date:2019/12/28_10:28
     */
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String login(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        return orderService.login(username,password);
    }



}

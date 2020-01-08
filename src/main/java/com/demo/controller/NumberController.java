package com.demo.controller;

import com.demo.service.NumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/number")
@Component
public class NumberController {

    @Autowired
    private NumberService numberService;

    /*
     *@Author:xjy
     *@Description:获取号码列表
     *@Date:2019/12/23_11:10
     */
    @RequestMapping(value = "/getNumberList", method = {RequestMethod.GET, RequestMethod.POST})
    public String getNumberList(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String cardNo = request.getParameter("cardNo");
        String cardTypeid = request.getParameter("cardTypeid");
        String companyid = request.getParameter("companyid");
        String cardState = request.getParameter("cardState");
        String pageIndex = request.getParameter("pageIndex");
        String pageSize = request.getParameter("pageSize");

        return numberService.getNumberList(startTime, endTime, cardNo, cardTypeid, companyid, cardState, pageIndex, pageSize);
    }

    /*
     *@Author:xjy
     *@Description:获取全部号码
     *@Date:2019/12/23_14:55
     */
    @RequestMapping(value = "/getNumberListAll", method = {RequestMethod.GET, RequestMethod.POST})
    public String getNumberListAll(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        String cardTypeId = request.getParameter("cardTypeId");
        String companyId = request.getParameter("companyId");
        String cardState = request.getParameter("cardState");

        return numberService.getNumberListAll(cardTypeId, companyId, cardState);
    }

    /*
     *@Author:xjy
     *@Description:删除号卡
     *@Date:2019/12/23_15:27
     */
    @RequestMapping(value = "/deleteNumber", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteNumber(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        String cardId = request.getParameter("cardId");

        return numberService.deleteNumber(cardId);
    }

    /*
     *@Author:xjy
     *@Description:添加号卡
     *@Date:2019/12/23_15:28
     */
    @RequestMapping(value = "/addNumber", method = {RequestMethod.GET, RequestMethod.POST})
    public String addNumber(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        String cardNo = request.getParameter("cardNo");
        String password = request.getParameter("password");
        String cardTypeId = request.getParameter("cardTypeId");
        String companyId = request.getParameter("companyId");
        String cardState = request.getParameter("cardState");

        return numberService.addNumber(cardNo, password, cardTypeId, companyId, cardState);
    }

    /*
     *@Author:xjy
     *@Description:修改号卡信息
     *@Date:2019/12/23_15:57
     */
    @RequestMapping(value = "/updateNumber", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateNumber(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        String cardId = request.getParameter("cardId");
        String cardNo = request.getParameter("cardNo");
        String password = request.getParameter("password");
        String cardTypeId = request.getParameter("cardTypeId");
        String companyId = request.getParameter("companyId");
        String cardState = request.getParameter("cardState");

        return numberService.updateNumber(cardId, cardNo, password, cardTypeId, companyId, cardState);
    }

    /*
     *@Author:xjy
     *@Description:批量生成卡号
     *@Date:2019/12/24_19:53
     */
    @RequestMapping(value = "/generateCardBatch", method = {RequestMethod.GET, RequestMethod.POST})
    public String generateCardBatch(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        String cardTypeId = request.getParameter("cardTypeId");
        String companyId = request.getParameter("companyId");
        String number = request.getParameter("number");

        return numberService.generateCardBatch(cardTypeId, companyId, number);
    }



}

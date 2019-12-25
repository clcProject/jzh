package com.demo.controller;

import com.demo.service.CompanyService;
import com.demo.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/type")
@Component
public class TypeController {

    @Autowired
    private TypeService typeService;


    /*
     *@Author:xjy
     *@Description:获取卡种类列表
     *@Date:2019/12/23_11:10
     */
    @RequestMapping(value = "/getTypeList", method = {RequestMethod.GET, RequestMethod.POST})
    public String getTypeList(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String cardName = request.getParameter("cardName");
        String companyId = request.getParameter("companyId");
        String pageIndex = request.getParameter("pageIndex");
        String pageSize = request.getParameter("pageSize");

        return typeService.getTypeList(startTime, endTime, cardName, companyId, pageIndex, pageSize);
    }

    /*
     *@Author:xjy
     *@Description:获取指定公司下卡种类
     *@Date:2019/12/23_14:55
     */
    @RequestMapping(value = "/getTypeListAll", method = {RequestMethod.GET, RequestMethod.POST})
    public String getTypeListAll(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        String companyId = request.getParameter("companyId");

        return typeService.getTypeListAll(companyId);
    }

    /*
     *@Author:xjy
     *@Description:删除卡种类
     *@Date:2019/12/23_15:27
     */
    @RequestMapping(value = "/deleteType", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteType(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        String cardTypeId = request.getParameter("cardTypeId");

        return typeService.deleteType(cardTypeId);
    }

    /*
     *@Author:xjy
     *@Description:添加卡种类
     *@Date:2019/12/23_15:28
     */
    @RequestMapping(value = "/addType", method = {RequestMethod.GET, RequestMethod.POST})
    public String addCompany(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        String cardName = request.getParameter("cardName");
        String companyId = request.getParameter("companyId");
        String content = request.getParameter("content");

        return typeService.addType(cardName, companyId,content);
    }

    /*
     *@Author:xjy
     *@Description:修改卡种类信息
     *@Date:2019/12/23_15:57
     */
    @RequestMapping(value = "/updateType", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateCompany(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        String cardTypeId = request.getParameter("cardTypeId");
        String cardName = request.getParameter("cardName");
        String companyId = request.getParameter("companyId");
        String content = request.getParameter("content");

        return typeService.updateType(cardTypeId,cardName, companyId, content);
    }

}

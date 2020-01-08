package com.demo.controller;

import com.demo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/company")
@Component
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /*
     *@Author:xjy
     *@Description:获取公司列表
     *@Date:2019/12/23_11:10
     */
    @RequestMapping(value = "/getCompanyList", method = {RequestMethod.GET, RequestMethod.POST})
    public String getCompanyList(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String companyName = request.getParameter("companyName");
        String companyAddress = request.getParameter("companyAddress");
        String pageIndex = request.getParameter("pageIndex");
        String pageSize = request.getParameter("pageSize");

        return companyService.getCompanyList(startTime, endTime,companyName,companyAddress,pageIndex,pageSize);
    }

    /*
     *@Author:xjy
     *@Description:获取全部公司
     *@Date:2019/12/23_14:55
     */
    @RequestMapping(value = "/getCompanyListAll", method = {RequestMethod.GET, RequestMethod.POST})
    public String getCompanyListAll(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        return companyService.getCompanyListAll();
    }

    /*
     *@Author:xjy
     *@Description:删除公司
     *@Date:2019/12/23_15:27
     */
    @RequestMapping(value = "/deleteCompany", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteCompany(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        String companyId = request.getParameter("companyId");

        return companyService.deleteCompany(companyId);
    }

    /*
     *@Author:xjy
     *@Description:添加公司
     *@Date:2019/12/23_15:28
     */
    @RequestMapping(value = "/addCompany", method = {RequestMethod.GET, RequestMethod.POST})
    public String addCompany(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        String companyName = request.getParameter("companyName");
        String companyAddress = request.getParameter("companyAddress");

        return companyService.addCompany(companyName,companyAddress);
    }

    /*
     *@Author:xjy
     *@Description:修改公司信息
     *@Date:2019/12/23_15:57
     */
    @RequestMapping(value = "/updateCompany", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateCompany(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/plain;charset=utf-8");

        String companyid = request.getParameter("companyId");
        String companyName = request.getParameter("companyName");
        String companyAddress = request.getParameter("companyAddress");

        return companyService.updateCompany(companyid,companyName,companyAddress);
    }



}

package com.demo.controller;

import com.demo.service.TaocanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/taocan")
@Component
public class TaocanController {

    @Autowired
    private TaocanService taocanService;

    /*
     *@Author:xjy
     *@Description:获取套餐列表
     *@Date:2019/12/23_11:10
     */
    @RequestMapping(value = "/getTaocanList", method = {RequestMethod.GET, RequestMethod.POST})
    public String getTaocanList(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String taocanName = request.getParameter("taocanName");
        String cardTypeid = request.getParameter("cardTypeid");
        String companyid = request.getParameter("companyid");
        String pageIndex = request.getParameter("pageIndex");
        String pageSize = request.getParameter("pageSize");

        return taocanService.getTaocanList(startTime, endTime,taocanName,cardTypeid,companyid,pageIndex,pageSize);
    }

    /*
     *@Author:xjy
     *@Description:获取全部套餐
     *@Date:2019/12/23_14:55
     */
    @RequestMapping(value = "/getTaocanListAll", method = {RequestMethod.GET, RequestMethod.POST})
    public String getTaocanListAll(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        String cardTypeId = request.getParameter("cardTypeId");
        String companyId = request.getParameter("companyId");

        return taocanService.getTaocanListAll(cardTypeId,companyId);
    }

    /*
     *@Author:xjy
     *@Description:删除套餐
     *@Date:2019/12/23_15:27
     */
    @RequestMapping(value = "/deleteTaocan", method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteTaocan(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        String taocanId = request.getParameter("taocanId");

        return taocanService.deleteTaocan(taocanId);
    }

    /*
     *@Author:xjy
     *@Description:添加套餐
     *@Date:2019/12/23_15:28
     */
    @RequestMapping(value = "/addTaocan", method = {RequestMethod.GET, RequestMethod.POST})
    public String addTaocan(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        String taocanName = request.getParameter("taocanName");
        String cardTypeId = request.getParameter("cardTypeId");
        String companyId = request.getParameter("companyId");
        String content = request.getParameter("content");
        String imgBase64 = request.getParameter("imgBase64");

        return taocanService.addTaocan(taocanName,cardTypeId,companyId,content,imgBase64);
    }

    /*
     *@Author:xjy
     *@Description:修改套餐信息
     *@Date:2019/12/23_15:57
     */
    @RequestMapping(value = "/updateTaocan", method = {RequestMethod.GET, RequestMethod.POST})
    public String updateTaocan(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");

        String taocanId = request.getParameter("taocanId");
        String taocanName = request.getParameter("taocanName");
        String cardTypeId = request.getParameter("cardTypeId");
        String companyId = request.getParameter("companyId");
        String content = request.getParameter("content");
        String imgBase64 = request.getParameter("imgBase64");

        return taocanService.updateTaocan(taocanId,taocanName,cardTypeId,companyId,content,imgBase64);
    }


}

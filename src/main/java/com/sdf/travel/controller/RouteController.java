package com.sdf.travel.controller;

import com.sdf.travel.domain.PageBean;
import com.sdf.travel.domain.Route;
import com.sdf.travel.service.RouteService;
import com.sdf.travel.util.ErrorMsgHouse;
import com.sdf.travel.util.ResultInfo;
import com.sdf.travel.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/route")
public class RouteController {

    @Autowired
    private RouteService service;

    private ResultInfo info;

    private String json;
    /**
     * 分页展示数据
     * @param currentPage
     * @param pageSize
     * @param cid
     * @param resp
     */
    @RequestMapping("/pageQuery")
    public void pageQuery(Integer currentPage,Integer pageSize,Integer cid,String rname,HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json;charset=utf-8");

        if (rname != null)
            rname = new String(rname.getBytes("ISO8859-1"),"utf-8");

        if (cid == null) {

            throw new RuntimeException("cid is null");
        }

        if (pageSize == null) pageSize = 5;

        if (currentPage == null) currentPage = 1;

        PageBean<Route> pageBean = service.getPageBean(currentPage, pageSize, cid,rname);
        String json = ServletUtil.getJson(pageBean);

        resp.getWriter().write(json);


    }


}

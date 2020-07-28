package com.sdf.travel.controller;

import com.sdf.travel.domain.PageBean;
import com.sdf.travel.domain.Route;
import com.sdf.travel.domain.User;
import com.sdf.travel.service.FavoriteService;
import com.sdf.travel.service.RouteService;
import com.sdf.travel.util.ErrorMsgHouse;
import com.sdf.travel.util.ResultInfo;
import com.sdf.travel.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/route")

public class RouteController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private FavoriteService favoriteService;

    private ResultInfo info;

    private String json;
    /**
     * 分页展示数据
     * @param currentPage
     * @param pageSize
     * @param cid
     */
    @ResponseBody
    @RequestMapping("/pageQuery")
    public PageBean<Route> pageQuery(Integer currentPage,Integer pageSize,Integer cid,String rname) throws IOException {



        if("null".equals(rname)) rname = null;
//          tomcat8之后，解决了get请求乱码问题
//        if (rname != null)
//            rname = new String(rname.getBytes("ISO8859-1"),"utf-8");

        if (cid == null) {

            throw new RuntimeException("cid is null");
        }

        if (pageSize == null) pageSize = 5;

        if (currentPage == null) currentPage = 1;

        PageBean<Route> pageBean = routeService.getPageBean(currentPage, pageSize, cid,rname);
        return pageBean;


    }
    @ResponseBody
    @RequestMapping("/detialQuery")
    public Route findDetial(Integer rid) throws IOException {

        if (rid == null)
            throw new RuntimeException("rid is null");

        Route detial = routeService.findDetial(rid);


        return detial;
    }

    /**
     * 判断用户是否收藏线路
     * @param rid
     */
    @ResponseBody
    @RequestMapping("isFavorite")
    public Boolean isFavorite(Integer rid, HttpSession session) throws IOException {

        //检查用户登录
        User user = (User) session.getAttribute("user");
        if (user == null){

            return false;
        }
        //查询用户是否收藏
        if (favoriteService.isFavorite(rid,user.getUid())){

           return true;
        }else {

            return false;
        }
    }



}

package com.sdf.travel.controller;

import com.sdf.travel.domain.User;
import com.sdf.travel.service.FavoriteService;
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
import java.io.Serializable;

@Controller
@RequestMapping("/favorite")

public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    private ResultInfo info;

    private String json;

    @ResponseBody
    @RequestMapping("/add")
    public ResultInfo add(Integer rid, HttpSession session, HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json;charset=utf-8");

        //判断用户是否登录
        User user = (User) session.getAttribute("user");
        if (user == null){
            info = ServletUtil.getInfo(false,null, ErrorMsgHouse.loginTipMsg);

            return info;
        }
        if (favoriteService.addFavorite(rid, user.getUid())) {
            info = ServletUtil.getInfo(true,null, "");
            return info;
        }else {
            info = ServletUtil.getInfo(false,null, ErrorMsgHouse.serverBusyErrorMsg);
            return info;
        }
    }
}

package com.sdf.travel.controller;

import com.sdf.travel.domain.User;
import com.sdf.travel.service.UserService;
import com.sdf.travel.util.ErrorMsgHouse;
import com.sdf.travel.util.ResultInfo;
import com.sdf.travel.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    private ResultInfo info;

    private String json;

    @RequestMapping("/regist")
    public void Regist(HttpSession session, HttpServletResponse resp, String check,User user) throws IOException {


        resp.setContentType("application/json;charset=utf-8");
        //校验验证码
        if (!ckeckCKCode(session,resp,check)) return;

        if (service.UserRegist(user)) {
            info = ServletUtil.getInfo(true,null,"");
            json = ServletUtil.getJson(info);
            resp.getWriter().write(json);
        }else {
            info = ServletUtil.getInfo(false,null, ErrorMsgHouse.nameExistMsg);
            json = ServletUtil.getJson(info);
            resp.getWriter().write(json);
        }
    }

    @RequestMapping("/active")
    public void active(HttpServletRequest req,HttpServletResponse resp, String code) throws IOException {

        resp.setContentType("text/html;charset=utf-8");

        if (service.userActive(code)) {

            resp.sendRedirect(req.getContextPath()+"/active_ok.html");

        }else {
            resp.getWriter().write("激活码失效，请联系管理员！");
        }

    }

    @RequestMapping("/login")
    public void login(){

    }


    private Boolean ckeckCKCode(HttpSession session, HttpServletResponse resp, String check) throws IOException {
        //验证码校验
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server == null){
            info = ServletUtil.getInfo(false,null, ErrorMsgHouse.ckCodeInvalidMsg);
            json = ServletUtil.getJson(info);
            resp.getWriter().write(json);
            return false;
        }
        if (check == null || "".equals(check)){
            info = ServletUtil.getInfo(false,null, ErrorMsgHouse.ckCodeIsNullMsg);
            json = ServletUtil.getJson(info);
            resp.getWriter().write(json);
            return false;
        }
        if (!check.equalsIgnoreCase(checkcode_server)){
            info = ServletUtil.getInfo(false,null, ErrorMsgHouse.ckCodeErrorMsg);
            json = ServletUtil.getJson(info);
            resp.getWriter().write(json);
            return false;
        }
        return true;
    }


}

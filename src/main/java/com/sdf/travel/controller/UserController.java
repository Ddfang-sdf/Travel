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

    /**
     * 登陆
     * 1、判断用户是否存在
     * 2、判断用户是否激活
     * 3、保存用户session
     * 4、响应到首页
     */
    @RequestMapping("/login")
    public void login(User user,String check,String auto_login,HttpServletResponse resp,HttpSession session) throws IOException {

        resp.setContentType("application/json;charset=utf-8");

        if (!ckeckCKCode(session,resp,check)) return;

        if (!service.userExist(user.getUsername())) {
            info = ServletUtil.getInfo(false,null,ErrorMsgHouse.noRegistErrorMsg);
            json = ServletUtil.getJson(info);
            resp.getWriter().write(json);
            return;
        }

        if (!service.userIsActive(user.getUsername())){
            info = ServletUtil.getInfo(false,null,ErrorMsgHouse.noActiveErrorMsg);
            json = ServletUtil.getJson(info);
            resp.getWriter().write(json);
            return;
        }

        User userLogin = service.userLogin(user);

        if (userLogin == null) {
            info = ServletUtil.getInfo(false,null,ErrorMsgHouse.loginErrorMsg);
            json = ServletUtil.getJson(info);
            resp.getWriter().write(json);
            return;
        }else {

            info = ServletUtil.getInfo(true,null,"");
            json = ServletUtil.getJson(info);
            resp.getWriter().write(json);

        }

        //判断用户是否允许自动登陆
        if (auto_login != null && !"".equals(auto_login)){

            session.setAttribute("user",userLogin);

            session.setAttribute("autoLogin",true);
        }else {

            session.setAttribute("user",userLogin);

            session.setAttribute("autoLogin",false);
        }



    }

    /**
     * 校验用户是否自动登陆
     * @param session
     * @param resp
     * @throws IOException
     */
    @RequestMapping("/autoLogin")
    public void autoLogin(HttpSession session,HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json;charset=utf-8");

        if (session.getAttribute("user") == null) return;

        if (!(Boolean) session.getAttribute("autoLogin")) return;

        info = ServletUtil.getInfo(true,null,"");

        json = ServletUtil.getJson(info);

        resp.getWriter().write(json);

    }

    @RequestMapping("/welTip")
    public void weiTip(HttpSession session,HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json;charset=utf-8");

        User user = (User) session.getAttribute("user");

        if (user == null) return;

        info = ServletUtil.getInfo(true,user.getName(),"");

        json = ServletUtil.getJson(info);

        resp.getWriter().write(json);

    }

    @RequestMapping("/exit")
    public void exit(HttpSession session,HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=utf-8");

        session.removeAttribute("user");

        session.removeAttribute("autoLogin");

        info = ServletUtil.getInfo(true,null,"");

        json = ServletUtil.getJson(info);

        resp.getWriter().write(json);

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

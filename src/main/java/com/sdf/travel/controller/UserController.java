package com.sdf.travel.controller;

import com.sdf.travel.domain.User;
import com.sdf.travel.service.UserService;
import com.sdf.travel.util.ErrorMsgHouse;
import com.sdf.travel.util.ResultInfo;
import com.sdf.travel.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/user")
@ResponseBody
public class UserController {

    @Autowired
    private UserService service;

    private ResultInfo info;


    @RequestMapping("/regist")
    public ResultInfo Regist(HttpSession session, HttpServletResponse resp, String check,User user) throws IOException {

        //校验验证码
        if (!ckeckCKCode(session,resp,check)){
            info = ServletUtil.getInfo(false,null,ErrorMsgHouse.ckCodeErrorMsg);
            return info;
        }

        if (service.UserRegist(user)) {
            info = ServletUtil.getInfo(true,null,"");
            return info;
        }else {
            info = ServletUtil.getInfo(false,null, ErrorMsgHouse.nameExistMsg);
            return info;
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
    public ResultInfo login(User user,String check,String auto_login,HttpServletResponse resp,HttpSession session) throws IOException {

        resp.setContentType("application/json;charset=utf-8");

        if (!ckeckCKCode(session,resp,check)) {
            info = ServletUtil.getInfo(false,null,ErrorMsgHouse.ckCodeErrorMsg);
            return info;
        }

        if (!service.userExist(user.getUsername())) {
            info = ServletUtil.getInfo(false,null,ErrorMsgHouse.noRegistErrorMsg);
           return info;
        }

        if (!service.userIsActive(user.getUsername())){
            info = ServletUtil.getInfo(false,null,ErrorMsgHouse.noActiveErrorMsg);
            return info;
        }

        User userLogin = service.userLogin(user);

        if (userLogin == null) {
            info = ServletUtil.getInfo(false,null,ErrorMsgHouse.loginErrorMsg);
            return info;
        }


        //判断用户是否允许自动登陆
        if (auto_login != null && !"".equals(auto_login)){

            session.setAttribute("user",userLogin);

            session.setAttribute("autoLogin",true);
        }else {

            session.setAttribute("user",userLogin);

            session.setAttribute("autoLogin",false);
        }

        info = ServletUtil.getInfo(true,null,"");
        return info;

    }

    /**
     * 校验用户是否自动登陆
     * @param session
     * @param resp
     * @throws IOException
     */
    @RequestMapping("/autoLogin")
    public ResultInfo autoLogin(HttpSession session,HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json;charset=utf-8");

        if (session.getAttribute("user") == null) return null;

        if (!(Boolean) session.getAttribute("autoLogin")) return null;

        info = ServletUtil.getInfo(true,null,"");

        return info;

    }

    @RequestMapping("/welTip")
    public ResultInfo weiTip(HttpSession session,HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json;charset=utf-8");

        User user = (User) session.getAttribute("user");

        if (user == null) return null;

        info = ServletUtil.getInfo(true,user.getName(),"");

        return info;

    }

    @RequestMapping("/exit")
    public ResultInfo exit(HttpSession session,HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json;charset=utf-8");

        session.removeAttribute("user");

        session.removeAttribute("autoLogin");

        info = ServletUtil.getInfo(true,null,"");

        return info;

    }

    private Boolean ckeckCKCode(HttpSession session, HttpServletResponse resp, String check) throws IOException {
        //验证码校验
        String checkcode_server = (String)session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server == null)

            return false;

        if (check == null || "".equals(check))

            return false;

        if (!check.equalsIgnoreCase(checkcode_server))

            return false;

        return true;
    }


}

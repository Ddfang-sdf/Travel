package com.sdf.travel.web.servlet;

import com.sdf.travel.domain.ResultInfo;
import com.sdf.travel.domain.User;
import com.sdf.travel.service.UserService;
import com.sdf.travel.service.impl.UserServiceImpl;
import com.sdf.travel.util.ErrorMsgHouse;
import com.sdf.travel.util.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends UserBaseServlet {
    private UserService service = new UserServiceImpl();
    private ResultInfo info;
    private String json = null;
    /**
     * 欢迎信息显示
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void welTip(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");
        HttpSession session = req.getSession(false);
        if (session == null) return;
        User user = (User) session.getAttribute("user");
        info = ServletUtils.getInfo(true,user,"");
        json = ServletUtils.getJson(info);
        resp.getWriter().write(json);


    }

    /**
     * 用户首次登陆
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //设置响应编码格式
        resp.setContentType("application/json;charset=utf-8");
        //获取session
        HttpSession session = req.getSession(false);

        //封装JavaBean
        Map<String, String[]> map = req.getParameterMap();
        User userBean = (User) ServletUtils.getBean(map);
        //session为null
        if (session == null) {
            info = ServletUtils.getInfo(false,null, ErrorMsgHouse.ckCodeInvalidMsg);
            json = ServletUtils.getJson(info);
            resp.getWriter().write(json);
            return;
        }


        //获取服务器端验证码
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //消除验证码
        session.removeAttribute("CHECKCODE_SERVER");
        //获取客户端验证码
        String check = req.getParameter("check");
        //校验验证码
        if (!ServletUtils.ck_code(checkcode_server, check)) {
            //返回false，验证码错误或为空
            info = ServletUtils.getInfo(false,null,ErrorMsgHouse.ckCodeErrorMsg);
            json = ServletUtils.getJson(info);
            resp.getWriter().write(json);
            return;
        }//校验通过
       //校验用户名密码
        User user = service.userLogin(userBean);
        if (user == null){
            //登录失败
            info = ServletUtils.getInfo(false,null,ErrorMsgHouse.loginErrorMsg);
            json = ServletUtils.getJson(info);
            resp.getWriter().write(json);
            return;
        }
        //登陆成功
        //将user对象存入session域中
        session.setAttribute("user",user);
        //判断用户是否要求自动登陆
        String auto_login = req.getParameter("auto_login");
        //用户要求自动登陆
        if (auto_login != null)
            session.setAttribute("autoLogin", true);
        else
            session.setAttribute("autoLogin", false);
        info = ServletUtils.getInfo(true,null,"");
        json = ServletUtils.getJson(info);
        resp.getWriter().write(json);

    }

    /**
     * 校验用户是否打开自动登陆功能
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void autoLogin(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");
        HttpSession session = req.getSession(false);
        if (session == null) return;
        Object autoLogin = session.getAttribute("autoLogin");
        //如果自动登陆标志为空，则说明用户尚未登陆过。
        if (autoLogin == null) return;
        boolean auto_flag = (boolean) autoLogin;
        //autoLogin不为null，说明用户以前登陆过，则根据用户的选择，进行不同的登陆校验
        if (auto_flag){
            //用户之前选择了自动登陆功能
            User _user = (User) session.getAttribute("user");

            User user = service.userLogin(_user);
            if (user != null){
                info = ServletUtils.getInfo(true,null,"");
                json = ServletUtils.getJson(info);
                session.setAttribute("user",user);
                resp.getWriter().write(json);
            }else{
                info = ServletUtils.getInfo(false,null,ErrorMsgHouse.loginTipMsg);
                json = ServletUtils.getJson(info);
                resp.getWriter().write(json);
            }
        }

    }

    /**
     * 用户注册
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //设置响应编码格式
        resp.setContentType("application/json;charset=utf-8");

        //获取服务器端用户验证码
        HttpSession session = req.getSession(false);
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //将session中的验证码删除
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server == null) {
            info = ServletUtils.getInfo(false,null,ErrorMsgHouse.ckCodeInvalidMsg);
            json = ServletUtils.getJson(info);
            //响应
            resp.getWriter().write(json);
            return;

        }
        //获取客户端验证码
        String check = req.getParameter("check");
        //校验验证码
        if (!ServletUtils.ck_code(checkcode_server, check)) {
            //验证码错误，设置响应对象
            info = ServletUtils.getInfo(false,null,ErrorMsgHouse.ckCodeErrorMsg);
            json = ServletUtils.getJson(info);
            //响应
            resp.getWriter().write(json);
            return;
        }
        //获取用户数据，封装JavaBean
        Map<String, String[]> map = req.getParameterMap();
        User user = (User) ServletUtils.getBean(map);
        //调用业务层方法注册用户
        if (!service.Userregist(user)) {
            //注册失败
            info = ServletUtils.getInfo(false,null,ErrorMsgHouse.nameExistMsg);
            json = ServletUtils.getJson(info);
            resp.getWriter().write(json);
            return;
        }
        //注册成功
        info = ServletUtils.getInfo(true,null,"");
        json = ServletUtils.getJson(info);
        //响应
        resp.getWriter().write(json);
    }

    /**
     * 激活账号
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String code = req.getParameter("code");
        if (code == null || "".equals(code)) {
            resp.getWriter().write(ErrorMsgHouse.activeCodeLoseMsg);
            return;
        }

        if (!service.userActive(code)) {
            //激活失败
            resp.getWriter().write(ErrorMsgHouse.activeCodeLoseMsg);
            return;
        }
        //激活成功
        resp.sendRedirect(req.getContextPath() + "/active_ok.html");
    }

    /**
     * 退出登陆操作
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest req,HttpServletResponse resp)
            throws ServletException,IOException{
        req.getSession().invalidate();
        info = ServletUtils.getInfo(true,null,"");
        json = ServletUtils.getJson(info);
        resp.getWriter().write(json);

    }

}

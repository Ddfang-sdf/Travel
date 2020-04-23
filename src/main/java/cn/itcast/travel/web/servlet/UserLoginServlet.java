package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.util.ServletUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 处理用户登陆请求
 * 1、校验验证码
 * 2、检查用户是否要求自动登陆--auto_login
 * 3、检验用户名密码
 * 4、跳转到首页
 */
@WebServlet("/UserLoginServlet")
public class UserLoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //获取Json核心对象ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        //创建相应数据对象
        ResultInfo info = new ResultInfo();
        //创建业务层对象
        UserService service = new UserServiceImpl();
        //设置响应编码格式
        resp.setContentType("application/json;charset=utf-8");
        //创建json响应字符串
        String json = null;
        //获取session
        HttpSession session = req.getSession(false);

        //封装JavaBean
        Map<String, String[]> map = req.getParameterMap();
        User bean = ServletUtils.getBean(map);
        //session为null
        if (session == null) {
            json = ServletUtils.setInfo(mapper, info, false, null, "验证码已失效，点击图片切换验证码");
            resp.getWriter().write(json);
            return;
        }
        //获取session域中的user对象
        User autoLgin_user = (User) session.getAttribute("user");
        //若该对象存在
        if (autoLgin_user != null){
            json = ServletUtils.setInfo(mapper,info,true,null,"");
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
            json = ServletUtils.setInfo(mapper, info, false, null, "验证码错误");
            resp.getWriter().write(json);
            return;
        }//校验通过
        //用户要求自动登陆
        if (req.getParameter("auto_login") != null) {


            //用户第一次登陆，且要求自动登陆，那么session中没有user对象
            if (autoLgin_user == null) {
                //校验用户名密码
                autoLgin_user = service.userLogin(bean);
                if (autoLgin_user == null) {
                    //用户名或密码错误
                    json = ServletUtils.setInfo(mapper, info, false, null, "用户名或密码错误");
                    resp.getWriter().write(json);
                    return;
                }
                //将user对象存储在session中
                session.setAttribute("user", autoLgin_user);

                //设置session有效时长，单位s----下面设置为30分钟
                session.setMaxInactiveInterval(60*30);
                //响应结果
                resp.getWriter().write(ServletUtils.setInfo(mapper, info, true, null, ""));
                return;
            }
        }
        //用户不要求自动登陆
        //校验用户名密码
        User user = service.userLogin(bean);
        if (user == null) {
            //用户名或密码错误
            json = ServletUtils.setInfo(mapper, info, false, null, "用户名或密码错误");
            resp.getWriter().write(json);
            return;
        }

        json = ServletUtils.setInfo(mapper,info,true,null,"");
        resp.getWriter().write(json);

    }
}

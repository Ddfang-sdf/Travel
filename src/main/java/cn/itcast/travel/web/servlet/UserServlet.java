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
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
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
        String json = null;
        User user = (User) session.getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getOutputStream(), user);
    }

    /**
     * 用户登陆
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest req, HttpServletResponse resp)
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
        if (autoLgin_user != null) {
            json = ServletUtils.setInfo(mapper, info, true, null, "");
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
                session.setMaxInactiveInterval(60 * 30);
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

        json = ServletUtils.setInfo(mapper, info, true, null, "");
        resp.getWriter().write(json);

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
//创建响应对象
        ResultInfo info = new ResultInfo();
        //设置响应编码格式
        resp.setContentType("application/json;charset=utf-8");
        //创建业务层对象
        UserService service = new UserServiceImpl();
        //创建Json核心对象
        ObjectMapper mapper = new ObjectMapper();
        //定义响应Json字符串
        String Json_info = null;
        //获取服务器端用户验证码
        HttpSession session = req.getSession(false);
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //将session中的验证码删除
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server == null) {
            Json_info = ServletUtils.setInfo(mapper, info, false, null, "验证码已失效，请点击更换");
            //响应
            resp.getWriter().write(Json_info);
            return;

        }
        //获取客户端验证码
        String check = req.getParameter("check");
        //校验验证码
        if (!ServletUtils.ck_code(checkcode_server, check)) {
            //验证码错误，设置响应对象
            Json_info = ServletUtils.setInfo(mapper, info, false, null, "验证码错误");
            //响应
            resp.getWriter().write(Json_info);
            return;
        }
        //获取用户数据，封装JavaBean
        Map<String, String[]> map = req.getParameterMap();
        User user = ServletUtils.getBean(map);
        //调用业务层方法注册用户
        if (!service.Userregist(user)){
            //注册失败
            Json_info = ServletUtils.setInfo(mapper, info, false, null, "该用户名已存在");
            resp.getWriter().write(Json_info);
            return;
        }
        //注册成功
        Json_info = ServletUtils.setInfo(mapper, info, true, null, "");
        //响应
        resp.getWriter().write(Json_info);
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
        if (code == null || "".equals(code)){
            resp.getWriter().write("激活码丢失，请重新在邮箱点击激活！");
            return;
        }
        UserService service = new UserServiceImpl();
        if (!service.userActive(code)){
            //激活失败
            resp.getWriter().write("激活码丢失，请重新在邮箱点击激活！");
            return;
        }
        //激活成功
        resp.sendRedirect(req.getContextPath()+"/active_ok.html");
    }

}

package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户激活
 * 1、验证激活码是否为空
 * 2、根据激活码查询用户信息
 * 3、判断是否存在该用户信息
 * 4、更改用户的激活状态
 * 5、跳转到激活成功页面
 *
 */
@WebServlet("/ActiveUserServlet")
public class ActiveUserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
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

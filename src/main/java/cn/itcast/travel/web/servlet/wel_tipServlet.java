package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * 回显用户信息
 */
@WebServlet("/wel_tipServlet")
public class wel_tipServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf-8");
        HttpSession session = req.getSession(false);
        if (session == null) return;
        String json = null;
        User user = (User) session.getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(resp.getOutputStream(),user);

    }
}

package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.util.ServletUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * 用户注册功能：
 * 1客户端发来用户表单数据
 * 2服务器接收数据
 * 3服务器校验验证码
 * 4服务器根据用户名查询用户名是否存在
 * 5服务器更新数据库
 * 6服务器响应结果
 */
@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
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


}

package cn.itcast.travel.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@WebServlet("/BaseServlet")
public class UserBaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //获取方法名
        String requestURI = req.getRequestURI();
        int index = requestURI.lastIndexOf("/");
        String methodName = requestURI.substring(index+1);
        //System.out.println(methodName);
        try {

            //通过方法名获取方法对象
            Method method = this.getClass().getMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            //执行方法
            method.invoke(this,req,resp);
            /**
             * java中的this最大的特点就是谁调用我，我就指谁。这里的this，是UserServlet对象，
             * 因为用户的uri指向的是UserServlet。
             * url指向一个servlet，则这个servlet会先调用自己的service方法，如果自己没有service
             * 方法，就会调用父类（HttpServlet）的service方法。通过阅读HttpServlet的源码可以知道，
             * HttpServlet也存在这这种‘请求分发’的动作。HttpServlet的service方法会先判断请求方式
             * 即调用request.getMethod()方法。然后根据不同的请求方式，调用不同的方法，比如doPost
             * 或doGet方法。
             * 这里的UserServlet是BaseServlet的子类，而BaseServlet是HttpServlet的子类。所以说
             * UserServlet和BaseServlet都是以前常常使用的servlet。
             * 当url请求访问UserServlet时，根据servlet的机制，会调用BaseServlet的service方法。
             * 我们就可以在Baseservlet中进行请求的分发动作。
             * 只不过我们不能向sun公司一样那么的方便，我们只能使用反射的机制来实现请求的分发。
             */
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}

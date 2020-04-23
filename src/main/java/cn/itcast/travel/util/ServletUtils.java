package cn.itcast.travel.util;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ServletUtils {
    /**
     * 封装用户数据成JavaBean对象
     * @param map
     * @return
     */
    public static User getBean(Map<String, String[]> map) {
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * @param mapper
     * @param info
     * @param flag
     * @param errorMsg
     */
    public static String setInfo(ObjectMapper mapper, ResultInfo info, boolean flag, Object data, String errorMsg) throws JsonProcessingException {

        info.setFlag(flag);

        if (data != null)
            info.setData(data);

        if (errorMsg != null && !"".equals(errorMsg))
            info.setErrorMsg(errorMsg);

        return mapper.writeValueAsString(info);
    }

    /**
     * 校验验证码
     *
     * @param checkcode_server
     * @param check
     * @return
     */
    public static boolean ck_code(String checkcode_server, String check) {
        if (checkcode_server != null) {
            if (check != null && check.equalsIgnoreCase(checkcode_server))
                //验证码正确
                return true;
        }

        return false;
    }
}

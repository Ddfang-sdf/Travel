package com.sdf.travel.util;

import com.sdf.travel.domain.ResultInfo;
import com.sdf.travel.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ServletUtils {
    private static ObjectMapper mapper;
    private static String json;
    private static ResultInfo info;

    /**
     *
     * @param flag
     * @param data
     * @param Msg
     * @return
     */
    public static ResultInfo getInfo(boolean flag,Object data,String Msg){
        info = new ResultInfo();
        info.setFlag(flag);
        info.setData(data);
        info.setErrorMsg(Msg);
        return info;
    }

    /**
     * 将数据序列化为json字符串
     * @param info
     * @return
     * @throws JsonProcessingException
     */
    public static String getJson(Object info) throws JsonProcessingException {
        mapper = new ObjectMapper();
        json = mapper.writeValueAsString(info);
        return json;
    }
    /**
     * 封装用户数据成JavaBean对象
     * @param map
     * @return
     */
    public static Object getBean(Map<String, String[]> map) {
        Object object = new User();
        try {
            BeanUtils.populate(object,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return object;
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

package com.sdf.travel.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ServletUtil {

    private static ResultInfo info;

    private static ObjectMapper mapper;

    public static ResultInfo getInfo(Boolean flag,Object data,String errorMsg){

      info = new ResultInfo();

      info.setFlag(flag);

      info.setData(data);

      info.setErrorMsg(errorMsg);

      return info;

    }

    public static String getJson(ResultInfo info){

        String json;

        mapper = new ObjectMapper();

        try {
            json = mapper.writeValueAsString(info);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("json序列化异常");
        }

        return json;
    }
}

package com.sdf.travel.dao;

import com.sdf.travel.domain.RouteImg;
import com.sdf.travel.domain.RouteImgExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RouteImgMapper {
    long countByExample(RouteImgExample example);

    int deleteByExample(RouteImgExample example);

    int deleteByPrimaryKey(Integer rgid);

    int insert(RouteImg record);

    int insertSelective(RouteImg record);

    List<RouteImg> selectByExample(RouteImgExample example);

    RouteImg selectByPrimaryKey(Integer rgid);

    int updateByExampleSelective(@Param("record") RouteImg record, @Param("example") RouteImgExample example);

    int updateByExample(@Param("record") RouteImg record, @Param("example") RouteImgExample example);

    int updateByPrimaryKeySelective(RouteImg record);

    int updateByPrimaryKey(RouteImg record);
}
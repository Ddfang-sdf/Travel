package com.sdf.travel.dao;

import com.sdf.travel.domain.Route;
import com.sdf.travel.domain.RouteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RouteMapper {
    long countByExample(RouteExample example);

    int deleteByExample(RouteExample example);

    int deleteByPrimaryKey(Integer rid);

    int insert(Route record);

    int insertSelective(Route record);

    List<Route> selectByExample(RouteExample example);

    Route selectByPrimaryKey(Integer rid);

    int updateByExampleSelective(@Param("record") Route record, @Param("example") RouteExample example);

    int updateByExample(@Param("record") Route record, @Param("example") RouteExample example);

    int updateByPrimaryKeySelective(Route record);

    int updateByPrimaryKey(Route record);
}
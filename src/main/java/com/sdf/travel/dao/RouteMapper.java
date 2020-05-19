package com.sdf.travel.dao;

import com.sdf.travel.domain.Route;
import com.sdf.travel.domain.RouteExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteMapper {

    @Select("select count(*) from tab_route where cid = #{cid}")
    Long findCount(Integer cid);

    @Select("select * from tab_route where cid = #{cid} limit #{start} ,#{pageSize}")
    List<Route> findAllByCid(@Param("cid") Integer cid,@Param("start") Integer start,@Param("pageSize") Integer pageSize);
}
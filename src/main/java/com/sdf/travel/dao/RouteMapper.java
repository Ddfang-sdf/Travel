package com.sdf.travel.dao;

import com.sdf.travel.domain.Route;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteMapper {

    Long findCount(@Param("cid") Integer cid,@Param("rname")String rname);

    List<Route> findAllByCid(@Param("cid") Integer cid,@Param("start") Integer start,@Param("pageSize") Integer pageSize,@Param("rname")String rname);
}
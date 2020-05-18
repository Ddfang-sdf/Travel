package com.sdf.travel.dao;

import com.sdf.travel.domain.Category;
import com.sdf.travel.domain.CategoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMapper {

    @Select("select * from tab_category")
    List<Category> findAll();

}
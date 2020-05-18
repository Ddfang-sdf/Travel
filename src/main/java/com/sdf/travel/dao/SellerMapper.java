package com.sdf.travel.dao;

import com.sdf.travel.domain.Seller;
import com.sdf.travel.domain.SellerExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SellerMapper {
    long countByExample(SellerExample example);

    int deleteByExample(SellerExample example);

    int deleteByPrimaryKey(Integer sid);

    int insert(Seller record);

    int insertSelective(Seller record);

    List<Seller> selectByExample(SellerExample example);

    Seller selectByPrimaryKey(Integer sid);

    int updateByExampleSelective(@Param("record") Seller record, @Param("example") SellerExample example);

    int updateByExample(@Param("record") Seller record, @Param("example") SellerExample example);

    int updateByPrimaryKeySelective(Seller record);

    int updateByPrimaryKey(Seller record);
}
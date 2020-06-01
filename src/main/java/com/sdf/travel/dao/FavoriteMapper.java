package com.sdf.travel.dao;


import com.sdf.travel.domain.Favorite;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteMapper {

    @Select("select * from tab_favorite where rid = #{rid} and uid = #{uid}")
    Favorite findByRidAndUid(@Param("rid") Integer rid, @Param("uid") Integer uid);

    @Insert("insert into tab_favorite values(#{rid},now(),#{uid})")
    Boolean addFavorite(@Param("rid") Integer rid,@Param("uid") Integer uid);
}
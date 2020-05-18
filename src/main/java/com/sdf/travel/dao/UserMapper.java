package com.sdf.travel.dao;

import com.sdf.travel.domain.User;
import com.sdf.travel.domain.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    /**
     * 根据用户名查询用户是否存在
     * @param username
     * @return
     */
    User findUserByUsername(String username);

    /**
     * 插入一条user
     * @param user
     * @return
     */
    Boolean InsertUserByUser(User user);

    /**
     * 根据code查询user
     * @param code
     * @return
     */
    @Select("select * from tab_user where code = #{code}")
    User findUserByCode(String code);

    @Update("update tab_user set status = 'Y'")
    boolean updateStatusByUser(User user);

    /**
     * 根据用户名密码查询用户
     * @param user
     * @return
     */
    @Select("select * from tab_user where username = #{username} and password = #{password}")
    User findUserByAccount(User user);
}
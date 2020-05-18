package com.sdf.travel.dao;

import com.sdf.travel.domain.User;

public interface UserDao {
    User findUsername(User user);

    boolean regist(User user);

    User findUserByCode(String code);

    boolean userActive(User user);

    User findByUsernameAndPassword(String username, String password);
}

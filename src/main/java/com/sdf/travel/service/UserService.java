package com.sdf.travel.service;

import com.sdf.travel.domain.User;

public interface UserService {

    Boolean UserRegist(User user);

    boolean userActive(String code);

    Boolean userExist(String username);

    boolean userIsActive(String username);

    User userLogin(User user);
}

package com.sdf.travel.service;

import com.sdf.travel.domain.User;

public interface UserService {
    boolean Userregist(User user);

    boolean userActive(String code);

    User userLogin(User bean);
}

package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    boolean Userregist(User user);

    boolean userActive(String code);

    User userLogin(User bean);
}

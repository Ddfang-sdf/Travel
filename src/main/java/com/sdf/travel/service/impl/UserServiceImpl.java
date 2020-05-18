package com.sdf.travel.service.impl;


import com.sdf.travel.dao.UserMapper;
import com.sdf.travel.domain.User;
import com.sdf.travel.service.UserService;
import com.sdf.travel.util.MailUtils;
import com.sdf.travel.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper mapper;

    /**
     * 用户注册业务
     * 1、判断用户是否存在
     * 2、插入用户数据
     * 3、给用户的邮箱发送邮件
     * 4、返回
     * @param user
     * @return
     */
    @Override
    public Boolean UserRegist(User user) {

        if (user == null) throw new RuntimeException("传入的user对象为null");

        User userByUsername = mapper.findUserByUsername(user.getUsername());

        if (userByUsername != null) return false;

        user.setCode(UuidUtil.getUuid());

        if (!mapper.InsertUserByUser(user))  return false;

        String text = "<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>【点击激活旅游网】</a>";

        MailUtils.sendMail(user.getEmail(),text,"旅游网账户激活");

        return true;
    }

    /**
     * 账号激活
     * 1、根据激活码查询用户是否存在
     * 2、修改用户激活状态
     * @param code
     * @return
     */
    @Override
    public boolean userActive(String code) {

        User user = mapper.findUserByCode(code);

        if (user == null) return false;

        return mapper.updateStatusByUser(user);
    }

    /**
     * 判断用户是否存在
     * @param username
     * @return
     */
    @Override
    public Boolean userExist(String username) {

        User user = mapper.findUserByUsername(username);

        return user != null ? true:false;
    }

    /**
     * 判断用户是否激活
     * @param username
     * @return
     */
    @Override
    public boolean userIsActive(String username) {

        User user = mapper.findUserByUsername(username);

        return "Y".equals(user.getStatus());
    }

    /**
     * 用户登陆
     * @param user
     * @return
     */
    @Override
    public User userLogin(User user) {

        return mapper.findUserByAccount(user);
    }
}

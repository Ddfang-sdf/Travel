package com.sdf.travel.service.impl;

import com.sdf.travel.dao.UserDao;
import com.sdf.travel.dao.impl.UserDaoImpl;
import com.sdf.travel.domain.User;
import com.sdf.travel.service.UserService;
import com.sdf.travel.util.MailUtils;
import com.sdf.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    UserDao dao = new UserDaoImpl();

    /**
     * 用户注册的业务
     * 首先校验用户名是否存在
     * 然后update数据库
     *
     * @return
     */
    @Override
    public boolean Userregist(User user) {
        if (dao.findUsername(user) != null)
            //用户名存在,注册失败,返回false
            return false;
        user.setStatus("N");
        user.setCode(UuidUtil.getUuid());
        MailUtils.sendMail(user.getEmail(),"<a href = 'http://localhost/travel/user/active?code="+user.getCode()+"'>激活</a>","激活");
        return dao.regist(user);
    }

    /**
     * 激活
     * 根据激活码查询用户信息，封装user对象
     * 根据user对象的uid修改该条数据的status为“Y”
     * @param code
     * @return
     */
    @Override
    public boolean userActive(String code) {
        User user = dao.findUserByCode(code);
        if (user == null) return false;
        return dao.userActive(user);

    }

    /**
     * 用户登陆
     * 根据bean的username和password判断是否存在该用户
     * 返回查询结果
     * @param bean
     * @return
     */
    @Override
    public User userLogin(User bean) {
        return dao.findByUsernameAndPassword(bean.getUsername(),bean.getPassword());
    }
}

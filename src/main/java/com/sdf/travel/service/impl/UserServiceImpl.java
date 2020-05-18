package com.sdf.travel.service.impl;

import com.sdf.travel.dao.UserDao;
import com.sdf.travel.dao.UserMapper;
import com.sdf.travel.dao.impl.UserDaoImpl;
import com.sdf.travel.domain.User;
import com.sdf.travel.service.UserService;
import com.sdf.travel.util.MailUtils;
import com.sdf.travel.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper mapper;
}

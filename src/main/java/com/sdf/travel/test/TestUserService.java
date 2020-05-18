package com.sdf.travel.test;

import com.sdf.travel.domain.User;
import com.sdf.travel.service.UserService;
import com.sdf.travel.service.impl.UserServiceImpl;
import org.junit.Test;



public class TestUserService {
    UserService service = new UserServiceImpl();
    @Test
    public void testRegist(){
//        User user = new User(5,"ssdf","ssssss","sdf","2019-3-24","男","111111111","sdf@sdf.com","s","s");
//        System.out.println(service.Userregist(user));
    }
    @Test
    public void testActive(){
        String code = "b5a6ca038b2645309e7a883a559214c";
        System.out.println(service.userActive(code));
    }
    @Test
    public void testuserLogin(){
        User user = new User();
        user.setUsername("sd");
        user.setPassword("ssssss");
        System.out.println(service.userLogin(user));
    }
    @Test
    public void testIfNull(){

       
    }


}

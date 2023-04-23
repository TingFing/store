package com.tingfeng.service;

import com.tingfeng.store.entity.User;
import com.tingfeng.store.service.IUserService;
import com.tingfeng.store.service.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserServiceTests {

    @Resource
    private IUserService userService;

    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("lisi1");
            user.setPassword("111");
            userService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login(){
        User jack = null;
        try {
            jack = userService.login("Jack", "111");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(jack);
    }

    @Test
    public void changUpdatePassword(){
        userService.changePassword(11,"manager","111","123");
    }

    @Test
    public void getByUid(){
        User user = userService.getByUid(13);
        System.err.println(user);
    }

    @Test
    public void changeInfo(){
        User user = new User();
        user.setPhone("13575643683");
        user.setEmail("12948@168.com");
        user.setGender(1);
        userService.changeInfo(11,"manager",user);
    }

    @Test
    public void updateAvatar(){
        userService.changeAvatar(13,"aaaa","Jack");
    }
}

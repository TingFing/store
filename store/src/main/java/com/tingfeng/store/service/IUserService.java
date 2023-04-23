package com.tingfeng.store.service;

import com.tingfeng.store.entity.User;

public interface IUserService {

    void reg(User user);

    User login(String username,String password);

    void changePassword(Integer uid,String username,String oldPassword,String newPassword);

    User getByUid(Integer uid);

    void changeInfo(Integer uid,String username,User user);

    void changeAvatar(Integer uid,String avatar, String username);
}

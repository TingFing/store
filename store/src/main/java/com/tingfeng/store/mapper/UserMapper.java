package com.tingfeng.store.mapper;

import com.tingfeng.store.entity.User;

import java.util.Date;

public interface UserMapper {

    Integer insert(User user);

    User findByUsername(String username);

    Integer updatePasswordByUid(Integer uid, String password, String modifiedUser, Date modifiedTime);

    User findByUid(Integer uid);

    Integer updateInfoByUid(User user);

    Integer updateAvatarByUid(Integer uid,String avatar, String modifiedUser, Date modifiedTime);
}

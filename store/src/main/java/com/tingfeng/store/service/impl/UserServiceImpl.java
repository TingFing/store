package com.tingfeng.store.service.impl;

import com.tingfeng.store.entity.User;
import com.tingfeng.store.mapper.UserMapper;
import com.tingfeng.store.service.IUserService;
import com.tingfeng.store.service.ex.*;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;
    @Override
    public void reg(User user) {
        String username = user.getUsername();
        User result = userMapper.findByUsername(username);
        if (result!=null) {
            throw new UsernameDuplicatedException("用户名被占用！");
        }

        String oldPassword = user.getPassword();
        //获取盐值
        String salt = UUID.randomUUID().toString().toUpperCase();

        //记录盐值,方便登录比对
        user.setSalt(salt);
        //加密处理
        String md5Password = getMD5Password(oldPassword, salt);
        //补全数据
        user.setPassword(md5Password);
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);
        Integer rows = userMapper.insert(user);
        if (rows!=1) {
            throw new InsertException("在用户注册过程中发生了未知异常！");
        }
    }

    @Override
    public User login(String username, String password) {
        User result = userMapper.findByUsername(username);
        if (result==null) {
            throw new UserNotFoundException("用户数据不存在！");
        }
        
        //比较密码
        String oldUsername = result.getPassword();
        String salt = result.getSalt();
        String newMd5Password = getMD5Password(password, salt);
        if (!newMd5Password.equals(oldUsername)) {
            throw new PasswordNotMatchException("用户密码错误！");
        }

        if (result.getIsDelete()==1) {
            throw new UserNotFoundException("用户数据不存在!");
        }

        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());

        //返回的数据是为了辅助其他页面做数据展示使用（uid，username，avatar）
        return user;
    }

    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if (result==null || result.getIsDelete()==1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        String oldMd5Password = getMD5Password(oldPassword, result.getSalt());
        if (!result.getPassword().equals(oldMd5Password)) {
            throw new PasswordNotMatchException("密码错误");
        }
        String newMd5Password = getMD5Password(newPassword, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, newMd5Password, username, new Date());
        if (rows!=1) {
            throw new UpdateException("更新数据异常");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if (result==null || result.getIsDelete()==1) {
            throw new UserNotFoundException("用户名不存在");
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if (result==null || result.getIsDelete()==1) {
            throw new UserNotFoundException("用户名不存在");
        }
        user.setUid(uid);
        //user.setUsername(username);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer rows = userMapper.updateInfoByUid(user);
        if (rows!=1) {
            throw new UpdateException("更新数据异常");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        User result = userMapper.findByUid(uid);
        if (result==null || result.getIsDelete()==1) {
            throw new UserNotFoundException("用户名不存在");
        }
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if (rows!=1) {
            throw new UpdateException("更新头像时发生的异常");
        }
    }

    //定义一个md5算法的加密处理
    private String getMD5Password(String password,String salt){
        for (int i = 0; i < 3; i++) {
            //md5方法的调用(进行三次加密)
            password = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
        return password;
    }
}

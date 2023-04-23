package com.tingfeng.mapper;

import com.tingfeng.store.entity.User;
import com.tingfeng.store.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

/**
 * SpringBootTest:表示当前的类是一个测试类，不会随同项目一起打包
 */
@SpringBootTest
public class UserMapperTests {

    @Resource
    private UserMapper userMapper;

    @Test
    public void insert(){
        User user = new User();
        user.setUsername("zs1");
        user.setPassword("111");
        Integer rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    public void findByUsername(){
        User zs = userMapper.findByUsername("zs");
        System.out.println(zs);
    }

    @Test
    public void updatePasswordByUid(){
        Integer zs = userMapper.updatePasswordByUid(1, "321", "manager", new Date());
        System.out.println(zs);
    }

    @Test
    public void findByUid(){
        System.out.println(userMapper.findByUid(1));
    }

    @Test
    public void updateInfoByUid(){
        User user = new User();
        user.setUid(13);
        user.setPhone("12356783247");
        user.setEmail("1242@qq.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }

    @Test
    public void updateAvatarByUid(){
        userMapper.updateAvatarByUid(13,"adfas","manager",new Date());
    }
}

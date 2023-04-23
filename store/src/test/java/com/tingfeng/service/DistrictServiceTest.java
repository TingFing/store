package com.tingfeng.service;

import com.tingfeng.store.entity.District;
import com.tingfeng.store.service.IDistrictService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class DistrictServiceTest {

    @Resource
    private IDistrictService districtService;

    @Test
    public void getByParent(){
        List<District> list = districtService.findByParent("86");
        list.forEach(l-> System.out.println(l));
    }
}

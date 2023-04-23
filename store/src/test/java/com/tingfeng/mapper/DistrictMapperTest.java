package com.tingfeng.mapper;

import com.tingfeng.store.entity.District;
import com.tingfeng.store.mapper.DistrictMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class DistrictMapperTest {

    @Resource
    private DistrictMapper districtMapper;

    @Test
    public void findByParent(){
        List<District> list = districtMapper.findByParent("110100");
        for (District district : list) {
            System.out.println(district);
        }
    }

    @Test
    public void  findNameByCode(){
        String name = districtMapper.findNameByCode("610000");
        System.err.println(name);
    }
}

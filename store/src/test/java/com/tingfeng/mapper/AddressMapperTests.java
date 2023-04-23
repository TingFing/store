package com.tingfeng.mapper;

import com.tingfeng.store.entity.Address;
import com.tingfeng.store.mapper.AddressMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class AddressMapperTests {

    @Resource
    private AddressMapper addressMapper;

    @Test
    public void insert(){
        Address address = new Address();
        address.setUid(13);
        address.setName("李四");
        address.setPhone("15697872358");
        addressMapper.insert(address);
    }

    @Test
    public void countByUid(){
        Integer integer = addressMapper.countByUid(13);
        System.out.println(integer);
    }

    @Test
    public void findByUid(){
        List<Address> list = addressMapper.findByUid(13);
        list.forEach(System.out::println);
    }

    @Test
    public void findByAid(){
        Address address = addressMapper.findByAid(13);
        System.out.println(address);
    }

    @Test
    public void updateNonDefault(){
        System.err.println(addressMapper.updateNonDefault(13));
    }

    @Test
    public void updateDefaultByAid(){
        System.err.println(addressMapper.updateDefaultByAid(13, "manager", new Date()));
    }

    @Test
    public void deleteByAid(){
        System.err.println(addressMapper.deleteByAid(12));
    }

    @Test
    public void findLastModified(){
        System.err.println(addressMapper.findLastModified(13));
    }
}

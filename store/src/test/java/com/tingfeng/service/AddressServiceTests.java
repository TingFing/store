package com.tingfeng.service;

import com.tingfeng.store.entity.Address;
import com.tingfeng.store.service.IAddressService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
public class AddressServiceTests {

    @Resource
    private IAddressService addressService;

    @Test
    public void addNewAddress(){
        Address address = new Address();
        address.setPhone("12654983546");
        address.setName("mark");
        addressService.addNewAddress(1,"message",address);
    }

    @Test
    public void setDefault(){
        addressService.setDefault(13,13,"manager");
    }

    @Test
    public void delete(){
        addressService.delete(11,13,"manager");
    }
}

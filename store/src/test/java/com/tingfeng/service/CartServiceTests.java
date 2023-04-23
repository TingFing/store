package com.tingfeng.service;

import com.tingfeng.store.entity.Address;
import com.tingfeng.store.service.IAddressService;
import com.tingfeng.store.service.ICartService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class CartServiceTests {

    @Resource
    private ICartService cartService;

    @Test
    public void addCart(){
        cartService.addToCart(6,10000006,1,"jack");
    }
}

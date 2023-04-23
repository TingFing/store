package com.tingfeng.service;

import com.tingfeng.store.entity.Address;
import com.tingfeng.store.entity.Order;
import com.tingfeng.store.service.IAddressService;
import com.tingfeng.store.service.IOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class OrderServiceTests {

    @Resource
    private IOrderService orderService;

    @Test
    public void create(){
        Integer[] cids = {1,3,5};
        Order o = orderService.create(43, 13, "jack", cids);
        System.err.println(o);
    }


}

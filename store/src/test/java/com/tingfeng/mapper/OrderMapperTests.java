package com.tingfeng.mapper;

import com.tingfeng.store.entity.Cart;
import com.tingfeng.store.entity.Order;
import com.tingfeng.store.entity.OrderItem;
import com.tingfeng.store.mapper.CartMapper;
import com.tingfeng.store.mapper.OrderMapper;
import com.tingfeng.store.vo.CartVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class OrderMapperTests {

    @Resource
    private OrderMapper orderMapper;

    @Test
    public void insertOrder(){
        Order order = new Order();
        order.setUid(13);
        order.setRecvName("mark");
        order.setRecvPhone("13787654897");
        System.err.println(orderMapper.insertOrder(order));
    }

    @Test
    public void insertOrderItem(){
        OrderItem orderItem = new OrderItem();
        orderItem.setOid(1);
        orderItem.setPid(10000004);
        orderItem.setTitle("得力（deli）1548A商务办公桌面计算器 太阳能双电源");
        System.err.println(orderMapper.insertOrderItem(orderItem));
    }
}

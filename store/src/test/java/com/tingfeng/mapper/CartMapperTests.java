package com.tingfeng.mapper;

import com.tingfeng.store.entity.Address;
import com.tingfeng.store.entity.Cart;
import com.tingfeng.store.mapper.AddressMapper;
import com.tingfeng.store.mapper.CartMapper;
import com.tingfeng.store.vo.CartVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class CartMapperTests {

    @Resource
    private CartMapper cartMapper;

    @Test
    public void insert(){
        Cart cart = new Cart();
        cart.setUid(13);
        cart.setPid(10000001);
        cart.setNum(2);
        cart.setPrice(100L);
        System.err.println(cartMapper.insert(cart));
    }

    @Test
    public void updateNumByCid(){
        System.err.println(cartMapper.updateNumByCid(1, 4, "jack", new Date()));
    }

    @Test
    public void findByUidAndPid(){
        System.err.println(cartMapper.findByUidAndPid(13, 10000001));
    }

    @Test
    public void findVOByUid(){
        List<CartVO> list = cartMapper.findVOByUid(13);
        list.forEach(System.out::println);
    }

    @Test
    public void findByCid(){
        System.err.println(cartMapper.findByCid(1));
    }

    @Test
    public void findVOByCid(){
        Integer[] cids = {1,3,5,7};
        List<CartVO> list = cartMapper.findVOByCid(cids);
        list.forEach(System.err::println);
    }
}

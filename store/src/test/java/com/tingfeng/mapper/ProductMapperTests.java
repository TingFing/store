package com.tingfeng.mapper;

import com.tingfeng.store.entity.Product;
import com.tingfeng.store.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class ProductMapperTests {

    @Resource
    private ProductMapper productMapper;
    @Test
    public void findHotList(){
        List<Product> hotList = productMapper.findHotList();
        hotList.forEach(System.out::println);
    }

    @Test
    public void findNewList(){
        List<Product> newList = productMapper.findNewList();
        newList.forEach(System.err::println);
    }
}

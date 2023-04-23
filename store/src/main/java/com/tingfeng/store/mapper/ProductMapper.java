package com.tingfeng.store.mapper;

import com.tingfeng.store.entity.Product;

import java.util.List;

public interface ProductMapper {

    List<Product> findHotList();

    Product findById(Integer id);

    List<Product> findNewList();
}

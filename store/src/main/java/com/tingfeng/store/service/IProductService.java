package com.tingfeng.store.service;

import com.tingfeng.store.entity.Product;

import java.util.List;

public interface IProductService {

    List<Product> findHotList();

    Product findById(Integer id);

    List<Product> findNewList();
}

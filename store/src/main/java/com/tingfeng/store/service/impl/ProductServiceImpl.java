package com.tingfeng.store.service.impl;

import com.tingfeng.store.entity.Product;
import com.tingfeng.store.mapper.ProductMapper;
import com.tingfeng.store.service.IProductService;
import com.tingfeng.store.service.ex.ProductNotFindException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Resource
    private ProductMapper productMapper;

    @Override
    public List<Product> findHotList() {
        List<Product> list = productMapper.findHotList();
        for (Product product : list) {
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return list;
    }

    @Override
    public Product findById(Integer id) {
        Product product = productMapper.findById(id);
        if (product==null) {
            throw new ProductNotFindException("尝试访问商品数据不存在");
        }
        product.setPriority(null);
        product.setCreatedUser(null);
        product.setCreatedTime(null);
        product.setModifiedUser(null);
        product.setModifiedTime(null);
        return product;
    }

    @Override
    public List<Product> findNewList() {
        List<Product> newList = productMapper.findNewList();
        for (Product product : newList) {
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return newList;
    }
}

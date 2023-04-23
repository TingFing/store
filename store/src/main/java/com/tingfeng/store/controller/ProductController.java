package com.tingfeng.store.controller;

import com.tingfeng.store.entity.Product;
import com.tingfeng.store.service.IProductService;
import com.tingfeng.store.utils.JsonResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController extends BaseController{

    @Resource
    private IProductService productService;

    @RequestMapping("/hot_list")
    public JsonResult<List<Product>> getHotList(){
        List<Product> data = productService.findHotList();
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("/{id}/details")
    public JsonResult<Product> getById(@PathVariable("id") Integer id){
        Product data = productService.findById(id);
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("/new_list")
    public JsonResult<List<Product>> getNewList(){
        List<Product> data = productService.findNewList();
        return new JsonResult<>(OK,data);
    }
}

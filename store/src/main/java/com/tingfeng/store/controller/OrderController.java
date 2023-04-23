package com.tingfeng.store.controller;

import com.tingfeng.store.entity.Order;
import com.tingfeng.store.service.IOrderService;
import com.tingfeng.store.utils.JsonResult;
import com.tingfeng.store.vo.OrderVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController{

    @Resource
    private IOrderService orderService;

    @RequestMapping("/create")
    public JsonResult<Order> create(Integer aid, Integer[] cids, HttpSession session){
        Order data = orderService.create(aid, getuidFromSession(session), getUsernameFromSession(session), cids);
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("/detail")
    public JsonResult<List<List<OrderVO>>> detail(HttpSession session){
        List<List<OrderVO>> data = orderService.findVoByUid(getuidFromSession(session));
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("/{id}/delete")
    public JsonResult<Void> deleteById(@PathVariable("id") Integer id){
        orderService.deleteById(id);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/update")
    public JsonResult<Void> updateByOid(Integer oid){
        orderService.updateByOid(oid);
        return new JsonResult<>(OK);
    }

    @RequestMapping("/pendingPayment")
    public JsonResult<List<List<OrderVO>>> pendingPayment(Integer status, HttpSession session){
        System.err.println(status);
        List<List<OrderVO>> data = orderService.findVOByStatus(getuidFromSession(session),status);
        data.forEach(System.err::println);
        return new JsonResult<>(OK,data);
    }
}

package com.tingfeng.store.controller;

import com.tingfeng.store.service.ICartService;
import com.tingfeng.store.utils.JsonResult;
import com.tingfeng.store.vo.CartVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController extends BaseController{

    @Resource
    private ICartService cartService;

    @RequestMapping("/add_to_cart")
    public JsonResult<Void> addToCart(Integer pid, Integer amount, HttpSession session){
        cartService.addToCart(getuidFromSession(session),pid,amount,getUsernameFromSession(session));
        return new JsonResult<>(OK);
    }

    @RequestMapping({"", "/"})
    public JsonResult<List<CartVO>> getVOByUid(HttpSession session) {
        Integer uid = getuidFromSession(session);
        List<CartVO> data = cartService.getVOByUid(uid);
        return new JsonResult<>(OK, data);
    }

    @RequestMapping("/{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session){
        Integer data = cartService.addNum(cid, getuidFromSession(session), getUsernameFromSession(session));
        return new JsonResult<>(OK,data);
    }

    @RequestMapping({ "/list"})
    public JsonResult<List<CartVO>> getVOByCid(Integer[] cids,HttpSession session) {
        Integer uid = getuidFromSession(session);
        List<CartVO> data = cartService.findVOByCid(uid, cids);
        return new JsonResult<>(OK, data);
    }
}

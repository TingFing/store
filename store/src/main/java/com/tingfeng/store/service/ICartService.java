package com.tingfeng.store.service;

import com.tingfeng.store.vo.CartVO;


import java.util.List;

public interface ICartService {

    void addToCart(Integer uid,Integer pid,Integer amount, String username);

    List<CartVO> getVOByUid(Integer uid);

    Integer addNum(Integer cid,Integer uid,String username);

    List<CartVO> findVOByCid(Integer uid , Integer[] cids);
}

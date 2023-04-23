package com.tingfeng.store.mapper;

import com.tingfeng.store.entity.Cart;
import com.tingfeng.store.vo.CartVO;

import java.util.Date;
import java.util.List;

public interface CartMapper {

    Integer insert(Cart cart);

    Integer updateNumByCid(Integer cid, Integer num, String modifiedUser, Date modifiedTime);

    Cart findByUidAndPid(Integer uid,Integer pid);

    List<CartVO> findVOByUid(Integer uid);

    Cart findByCid(Integer cid);

    List<CartVO> findVOByCid(Integer[] cids);
}

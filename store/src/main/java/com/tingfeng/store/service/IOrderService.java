package com.tingfeng.store.service;


import com.tingfeng.store.entity.Order;
import com.tingfeng.store.vo.OrderVO;

import java.util.List;

public interface IOrderService {

    Order create(Integer aid, Integer uid, String username, Integer[] cids);

    List<List<OrderVO>> findVoByUid(Integer uid);

    void deleteById(Integer id);

    void updateByOid(Integer oid);

    List<List<OrderVO>> findVOByStatus(Integer uid,Integer status);
}

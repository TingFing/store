package com.tingfeng.store.mapper;

import com.tingfeng.store.entity.Order;
import com.tingfeng.store.entity.OrderItem;
import com.tingfeng.store.vo.OrderVO;

import java.util.List;

public interface OrderMapper {

    Integer insertOrder(Order order);

    Integer insertOrderItem(OrderItem orderItem);

    List<OrderVO> findVoByUid(Integer uid);

    Integer deleteById(Integer id);

    Integer updateByOid(Integer oid);

    List<OrderVO> findVOByStatus(Integer uid,Integer status);
}

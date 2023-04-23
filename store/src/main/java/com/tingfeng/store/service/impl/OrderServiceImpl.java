package com.tingfeng.store.service.impl;

import com.tingfeng.store.entity.Address;
import com.tingfeng.store.entity.Order;
import com.tingfeng.store.entity.OrderItem;
import com.tingfeng.store.mapper.OrderMapper;
import com.tingfeng.store.service.IAddressService;
import com.tingfeng.store.service.ICartService;
import com.tingfeng.store.service.IOrderService;
import com.tingfeng.store.service.ex.InsertException;
import com.tingfeng.store.service.ex.UpdateException;
import com.tingfeng.store.vo.CartVO;
import com.tingfeng.store.vo.OrderVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private IAddressService addressService;

    @Resource
    private ICartService cartService;

    @Override
    public Order create(Integer aid, Integer uid, String username, Integer[] cids) {
        List<CartVO> list = cartService.findVOByCid(uid, cids);
        Long totalPrice = 0L;
        for (CartVO cartVO:list){
            totalPrice += cartVO.getRealPrice()*cartVO.getNum();
        }
        Address address = addressService.getByAid(aid, uid);
        Order order = new Order();
        order.setUid(uid);
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityCode());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address .getAddress());
        order.setStatus(0);
        order.setTotalPrice(totalPrice);
        order.setOrderTime(new Date());
        order.setCreatedUser(username);
        order.setCreatedTime(new Date());
        order.setModifiedUser(username);
        order.setModifiedTime(new Date());
        Integer rows = orderMapper.insertOrder(order);
        if (rows!=1) {
            throw new InsertException("插入数据异常！");
        }

        for (CartVO cart : list) {
            // 创建订单商品数据
            OrderItem orderItem = new OrderItem();
            orderItem.setOid(order.getOid());
            orderItem.setPid(cart.getPid());
            orderItem.setTitle(cart.getTitle());
            orderItem.setImage(cart.getImage());
            orderItem.setPrice(cart.getRealPrice());
            orderItem.setNum(cart.getNum());
            orderItem.setCreatedUser(username);
            orderItem.setIsDelete(1);
            orderItem.setCreatedTime(new Date());
            orderItem.setModifiedUser(username);
            orderItem.setModifiedTime(new Date());
            rows = orderMapper.insertOrderItem(orderItem);
            if (rows != 1) {
                throw new InsertException("插入订单商品数据时出现未知错误");
            }
        }
        return order;
    }

    @Override
    public List<List<OrderVO>> findVoByUid(Integer uid) {
        List<OrderVO> result = orderMapper.findVoByUid(uid);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (OrderVO orderVO : result) {
            Date orderTime = orderVO.getOrderTime();
            String format = sdf.format(orderTime);
            orderVO.setFormatTime(format);
        }
        List<List<OrderVO>> data = new ArrayList<>();

        for (int i = 0; i < result.size(); i++) {
            List<OrderVO> group = new ArrayList<>();
            group.add(result.get(i));
            for (int j = i+1; j < result.size(); j++) {
                if (result.get(j).getOid().equals(result.get(i).getOid())){
                    group.add(result.get(j));
                    i =j;
                }
            }
            data.add(group);
        }

        return data;
    }

    @Override
    public void deleteById(Integer id) {
        Integer rows = orderMapper.deleteById(id);
        if (rows!=1) {
            throw new UpdateException("更新数据异常");
        }
    }

    @Override
    public void updateByOid(Integer oid) {
        Integer rows = orderMapper.updateByOid(oid);
        if (rows!=1) {
            throw new UpdateException("更新数据异常");
        }
    }

    @Override
    public List<List<OrderVO>> findVOByStatus(Integer uid, Integer status) {
        List<OrderVO> result = orderMapper.findVOByStatus(uid,status);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (OrderVO orderVO : result) {
            Date orderTime = orderVO.getOrderTime();
            String format = sdf.format(orderTime);
            orderVO.setFormatTime(format);
        }
        List<List<OrderVO>> data = new ArrayList<>();

        for (int i = 0; i < result.size(); i++) {
            List<OrderVO> group = new ArrayList<>();
            group.add(result.get(i));
            for (int j = i+1; j < result.size(); j++) {
                if (result.get(j).getOid().equals(result.get(i).getOid())){
                    group.add(result.get(j));
                    i =j;
                }
            }
            data.add(group);
        }
        return data;
    }
}

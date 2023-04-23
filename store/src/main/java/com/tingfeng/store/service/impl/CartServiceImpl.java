package com.tingfeng.store.service.impl;

import com.tingfeng.store.entity.Cart;
import com.tingfeng.store.entity.Product;
import com.tingfeng.store.mapper.CartMapper;
import com.tingfeng.store.mapper.ProductMapper;
import com.tingfeng.store.service.ICartService;
import com.tingfeng.store.service.ex.AccessDeniedException;
import com.tingfeng.store.service.ex.CartNotFoundException;
import com.tingfeng.store.service.ex.InsertException;
import com.tingfeng.store.service.ex.UpdateException;
import com.tingfeng.store.vo.CartVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements ICartService {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private ProductMapper productMapper;
    @Override
    public void addToCart(Integer uid, Integer pid, Integer amount, String username) {
        //查询要添加的商品在购物车中是否存在
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        Date date = new Date();
        if (result==null) { //不存在执行新增操作
            Cart cart = new Cart();
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            Product product = productMapper.findById(pid);
            cart.setPrice(product.getPrice());
            cart.setCreatedUser(username);
            cart.setCreatedTime(date);
            cart.setModifiedUser(username);
            cart.setModifiedTime(date);
            Integer rows = cartMapper.insert(cart);
            if (rows!=1) {
                throw new InsertException("插入数据时产生未知的异常");
            }
        }else { // 存在执行更新操作
            Integer num = result.getNum() + amount;
            Integer rows = cartMapper.updateNumByCid(result.getCid(), num, username, date);
            if (rows!=1) {
                throw new UpdateException("更新数据时异常");
            }
        }
    }

    @Override
    public List<CartVO> getVOByUid(Integer uid) {
        List<CartVO> list = cartMapper.findVOByUid(uid);
        return list;
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if (result==null) {
            throw new CartNotFoundException("数据不存在") ;
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("数据非法访问") ;
        }
        Integer num = result.getNum() + 1;
        Integer rows = cartMapper.updateNumByCid(cid, num, username, new Date());
        if (rows!=1) {
            throw new UpdateException("更新数据时异常");
        }
        return  num;
    }

    @Override
    public List<CartVO> findVOByCid(Integer uid, Integer[] cids) {
        List<CartVO> list = cartMapper.findVOByCid(cids);
        Iterator<CartVO> iterator = list.iterator();
        while (iterator.hasNext()) {
            CartVO cartVO = iterator.next();
            if (!cartVO.getUid().equals(uid)) {
                list.remove(cartVO);
            }
        }
        return list;
    }
}

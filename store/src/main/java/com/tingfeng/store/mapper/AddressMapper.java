package com.tingfeng.store.mapper;

import com.tingfeng.store.entity.Address;

import java.util.Date;
import java.util.List;

public interface AddressMapper {

    Integer insert(Address address);

    /**
     * 根据用户的id统计收货地址的数量
     * @param uid
     * @return
     */
    Integer countByUid(Integer uid);

    List<Address> findByUid(Integer uid);

    Address findByAid(Integer aid);

    Integer updateNonDefault(Integer uid);

    Integer updateDefaultByAid(Integer aid, String modifiedUser, Date modifiedTime);

    Integer deleteByAid(Integer aid);

    Address findLastModified(Integer uid);
}

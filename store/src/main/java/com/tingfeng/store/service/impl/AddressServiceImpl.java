package com.tingfeng.store.service.impl;

import com.tingfeng.store.entity.Address;
import com.tingfeng.store.mapper.AddressMapper;
import com.tingfeng.store.service.IAddressService;
import com.tingfeng.store.service.IDistrictService;
import com.tingfeng.store.service.ex.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {

    @Resource
    private AddressMapper addressMapper;

    @Resource
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        Integer count = addressMapper.countByUid(uid);
        if (count >= maxCount) {
            throw new AddressCountLimitException("用户收货地址超出限制");
         }

        //对address中的数据补全：省市
        String province = districtService.findNameByCode(address.getProvinceCode());
        String city = districtService.findNameByCode(address.getCityCode());
        String area = districtService.findNameByCode(address.getAreaCode());
        address.setProvinceName(province);
        address.setCityName(city);
        address.setAreaName(area);

        address.setUid(uid);
        Integer isDefault = count == 0 ? 1 : 0;
        address.setIsDefault(isDefault);
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());

        Integer rows = addressMapper.insert(address);
        if (rows!=1) {
            throw new InsertException("插入用户的收货地址产生的异常");
        }
    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        for (Address address : list) {
            /*address.setAid(null);
            address.setUid(null);*/
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setTel(null);
            address.setIsDefault(null);
            address.setCreatedTime(null);
            address.setCreatedUser(null);
            address.setModifiedTime(null);
            address.setModifiedUser(null);
        }
        return list;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if (result==null) {
            throw new AddressNotFoundException("收货地址不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法数据访问");
        }
        Integer rows = addressMapper.updateNonDefault(uid);
        if (rows<1) {
            throw new UpdateException("更新数据异常");
        }
        rows = addressMapper.updateDefaultByAid(aid, username, new Date());
        if (rows!=1) {
            throw new UpdateException("更新数据异常");
        }
    }

    @Override
    public void delete(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if (result == null) {
            throw new AddressNotFoundException("尝试访问的收货地址数据不存在");
        }
        if (!result.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问");
        }
        Integer rows1 = addressMapper.deleteByAid(aid);
        if (rows1 != 1) {
            throw new DeleteException("删除收货地址数据时出现未知错误！");
        }
        if (result.getIsDefault() == 0) {
            return;
        }
        Integer count = addressMapper.countByUid(uid);
        if (count == 0) {
            return;
        }
        Address lastModified = addressMapper.findLastModified(uid);
        Integer lastModifiedAid = lastModified.getAid();
        Integer rows2 = addressMapper.updateDefaultByAid(lastModifiedAid, username, new Date());
        if (rows2 != 1) {
            throw new UpdateException("更新收货地址数据时出现未知错误");
        }
    }

    @Override
    public Address getByAid(Integer aid, Integer uid) {
        Address address = addressMapper.findByAid(aid);
        if (address==null) {
            throw new AddressNotFoundException("收货数据找不到");
        }
        if (!address.getUid().equals(uid)) {
            throw new AccessDeniedException("非法访问数据");
        }
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedTime(null);
        address.setModifiedUser(null);
        return address;
    }
}

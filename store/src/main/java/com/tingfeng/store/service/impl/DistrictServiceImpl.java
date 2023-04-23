package com.tingfeng.store.service.impl;

import com.tingfeng.store.entity.District;
import com.tingfeng.store.mapper.DistrictMapper;
import com.tingfeng.store.service.IDistrictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DistrictServiceImpl implements IDistrictService {
    
    @Resource
    private DistrictMapper districtMapper;
    
    @Override
    public List<District> findByParent(String parent) {
        List<District> list = districtMapper.findByParent(parent);
        /*把无效数据设置为null*/
        for (District district : list) {
            district.setId(null);
            district.setParent(null);
        }
        return list;
    }

    @Override
    public String findNameByCode(String code) {
        String name = districtMapper.findNameByCode(code);
        return name;
    }
}

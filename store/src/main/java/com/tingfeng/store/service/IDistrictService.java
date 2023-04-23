package com.tingfeng.store.service;

import com.tingfeng.store.entity.District;

import java.util.List;

public interface IDistrictService {
    List<District> findByParent(String parent);

    String findNameByCode(String code);
}

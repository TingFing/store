package com.tingfeng.store.controller;

import com.tingfeng.store.entity.District;
import com.tingfeng.store.service.IDistrictService;
import com.tingfeng.store.utils.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/district")
public class DistrictController extends BaseController{

    @Resource
    private IDistrictService districtService;

    @RequestMapping({"/", ""})
    public JsonResult<List<District>> getByParent(String parent){
        List<District> data = districtService.findByParent(parent);
        return new JsonResult<>(OK,data);
    }
}

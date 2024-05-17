package com.travel.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.entity.bus.BusInfo;
import com.travel.spzx.model.entity.product.Brand;

import java.util.List;

public interface BusService {
    PageInfo<BusInfo> findByPage(Integer page, Integer limit,String carNo,String driverName);

    void save(BusInfo brand);

    void delete(Integer id);

    void update(BusInfo brand);

    List<BusInfo> findAll();

}

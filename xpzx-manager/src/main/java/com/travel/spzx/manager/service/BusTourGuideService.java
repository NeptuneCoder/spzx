package com.travel.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.entity.bus.BusInfo;
import com.travel.spzx.model.entity.order.BusTourGuideInfo;

import java.util.List;

public interface BusTourGuideService {
    PageInfo<BusTourGuideInfo> findByPage(Integer page, Integer limit, String carNo, String driverName);

    void save(BusTourGuideInfo brand);

    void delete(Integer id);

    void update(BusTourGuideInfo brand);

    List<BusTourGuideInfo> findAll();

}

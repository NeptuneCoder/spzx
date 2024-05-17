package com.travel.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.entity.bus.BusInfo;
import com.travel.spzx.model.entity.order.BatchBusInfo;

import java.util.List;

public interface BatchBusService {
    PageInfo<BatchBusInfo> findByPage(Integer page, Integer limit, String carNo, String driverName);

    void save(BatchBusInfo brand);

    void delete(Integer id);

    void update(BatchBusInfo brand);

    List<BatchBusInfo> findAll();

}

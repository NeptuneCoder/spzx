package com.travel.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.entity.bus.BusInfo;
import com.travel.spzx.model.entity.order.BatchBusInfo;
import com.travel.spzx.model.entity.order.BatchBusInfoDto;

import java.util.List;

public interface BatchBusService {
    List<BatchBusInfo> findByPage(String batchId);

    void save(BatchBusInfoDto brand);

    void delete(String id, String carId, String batchId);

    void update(BatchBusInfo brand);

}

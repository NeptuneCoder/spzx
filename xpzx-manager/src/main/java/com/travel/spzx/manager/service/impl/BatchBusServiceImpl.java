package com.travel.spzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travel.spzx.manager.mapper.BatchBusMapper;
import com.travel.spzx.manager.mapper.BusMapper;
import com.travel.spzx.manager.service.BatchBusService;
import com.travel.spzx.manager.service.BusService;
import com.travel.spzx.model.entity.bus.BusInfo;
import com.travel.spzx.model.entity.order.BatchBusInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchBusServiceImpl implements BatchBusService {
    @Autowired
    private BatchBusMapper busMapper;

    @Override
    public PageInfo<BatchBusInfo> findByPage(Integer page, Integer limit, String carNo, String driverName) {
        PageHelper.startPage(page, limit);
        List<BatchBusInfo> brandList = busMapper.findByPage(carNo, driverName);
        return new PageInfo(brandList);
    }

    @Override
    public void save(BatchBusInfo brand) {
        busMapper.insertSelective(brand);
    }

    @Override
    public void delete(Integer id) {
        busMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(BatchBusInfo brand) {
        busMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public List<BatchBusInfo> findAll() {
        return busMapper.findAll();
    }
}

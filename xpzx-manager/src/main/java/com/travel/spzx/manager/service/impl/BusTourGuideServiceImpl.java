package com.travel.spzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travel.spzx.manager.mapper.BusTourGuideMapper;
import com.travel.spzx.manager.service.BusTourGuideService;
import com.travel.spzx.model.entity.order.BusTourGuideInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusTourGuideServiceImpl implements BusTourGuideService {
    @Autowired
    private BusTourGuideMapper busMapper;

    @Override
    public PageInfo<BusTourGuideInfo> findByPage(Integer page, Integer limit, String carNo, String driverName) {
        PageHelper.startPage(page, limit);
        List<BusTourGuideInfo> dataList = busMapper.findByPage(carNo, driverName);
        return new PageInfo(dataList);
    }

    @Override
    public void save(BusTourGuideInfo data) {
        busMapper.insertSelective(data);
    }

    @Override
    public void delete(Integer id) {
        busMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(BusTourGuideInfo data) {
        busMapper.updateByPrimaryKeySelective(data);
    }

    @Override
    public List<BusTourGuideInfo> findAll() {
        return busMapper.findAll();
    }
}

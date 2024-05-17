package com.travel.spzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travel.spzx.manager.mapper.BrandMapper;
import com.travel.spzx.manager.mapper.BusMapper;
import com.travel.spzx.manager.service.BrandService;
import com.travel.spzx.manager.service.BusService;
import com.travel.spzx.model.entity.bus.BusInfo;
import com.travel.spzx.model.entity.product.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusServiceImpl implements BusService {
    @Autowired
    private BusMapper busMapper;

    @Override
    public PageInfo<BusInfo> findByPage(Integer page, Integer limit,String carNo,String driverName) {
        PageHelper.startPage(page, limit);
        List<BusInfo> brandList = busMapper.findByPage(carNo,driverName);
        return new PageInfo(brandList);
    }

    @Override
    public void save(BusInfo brand) {
        busMapper.insertSelective(brand);
    }

    @Override
    public void delete(Integer id) {
        busMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(BusInfo brand) {
        busMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public List<BusInfo> findAll() {
        return busMapper.findAll();
    }
}

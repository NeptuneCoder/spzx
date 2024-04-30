package com.travel.spzx.manager.service.impl;

import com.travel.spzx.common.exception.GuiguException;
import com.travel.spzx.manager.mapper.CityInfoMapper;
import com.travel.spzx.manager.service.CityInfoService;
import com.travel.spzx.model.entity.city.CityInfo;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityInfoServiceImpl implements CityInfoService {
    @Autowired
    private CityInfoMapper cityInfoMapper;


    //根据分页获取城市列表信息
    @Override
    public PageInfo<CityInfo> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<CityInfo> data = cityInfoMapper.findByPage();
        PageInfo<CityInfo> pageInfo = new PageInfo<>(data);
        return pageInfo;
    }

    @Override
    public void save(CityInfo cityInfo) {
        //TODO 判断是否存在
        List<CityInfo> res = cityInfoMapper.findByCityName(cityInfo.getCityName());
        if (res.isEmpty()) {
            cityInfoMapper.save(cityInfo);
        } else {
            throw new GuiguException(ResultCodeEnum.ADDRESS_DUPLICATION_ERROR);
        }
    }

    @Override
    public void delete(Integer id) {
        cityInfoMapper.delete(id);
    }

    @Override
    public void update(CityInfo cityInfo) {
        cityInfoMapper.update(cityInfo);
    }

    @Override
    public List<CityInfo> getAll() {
        return cityInfoMapper.findByPage();
    }
}

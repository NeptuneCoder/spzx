package com.travel.spzx.manager.service;

import com.travel.spzx.model.entity.city.CityInfo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CityInfoService {
    PageInfo<CityInfo> findByPage(Integer page, Integer limit);

    void save(CityInfo cityInfo);

    void delete(Integer id);

    void update(CityInfo cityInfo);

    List<CityInfo> getAll();

}

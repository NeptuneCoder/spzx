package com.travel.spzx.manager.service;

import com.travel.spzx.model.entity.guide.TourLevel;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TourLevelService {
    void save(TourLevel tourLevel);

    PageInfo<TourLevel> findAll(Integer page, Integer limit);

    void delete(Integer id);

    void update(TourLevel data);

    List<TourLevel> getAllLevelList();

}

package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.TourGuideInfoMapper;
import com.atguigu.spzx.manager.service.TourGuideInfoService;
import com.atguigu.spzx.model.dto.guide.TourGuideInfoDto;
import com.atguigu.spzx.model.entity.guide.TourGuideInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TourGuideInfoServiceImpl implements TourGuideInfoService {
    @Autowired
    private TourGuideInfoMapper tourGuideInfoMapper;

    @Override
    public PageInfo<TourGuideInfo> findListByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<TourGuideInfo> list = tourGuideInfoMapper.findList();
        PageInfo<TourGuideInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void save(TourGuideInfo tourGuideInfo) {
        tourGuideInfoMapper.save(tourGuideInfo);
    }
}

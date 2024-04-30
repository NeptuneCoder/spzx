package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.mapper.TourGuideInfoMapper;
import com.atguigu.spzx.manager.mapper.TourLevelMapper;
import com.atguigu.spzx.manager.service.TourLevelService;
import com.atguigu.spzx.model.entity.guide.TourGuideInfo;
import com.atguigu.spzx.model.entity.guide.TourLevel;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TourLevelServiceImpl implements TourLevelService {
    @Autowired
    private TourLevelMapper tourLevelMapper;
    @Autowired
    private TourGuideInfoMapper tourGuideInfoMapper;

    @Override
    public void save(TourLevel data) {
        //TODO 新等级是否已经存在
        List<TourLevel> list = tourLevelMapper.queryByLevel(data.getLevel());
        if (list.isEmpty()) {
            tourLevelMapper.save(data);
        } else {
            throw new GuiguException(ResultCodeEnum.LEVEL_DUPLICATION_ERROR);
        }
    }

    @Override
    public PageInfo<TourLevel> findAll(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);

        return new PageInfo<TourLevel>(tourLevelMapper.findAll());
    }

    @Override
    public void delete(Integer id) {
        //TODO 需要判断是否有领队使用该等级
        tourLevelMapper.delete(id);
    }

    @Override
    public void update(TourLevel data) {
        //TODO 需要判断是否有领队使用该等级
        List<TourGuideInfo> guideList = tourGuideInfoMapper.queryGuideByLevel(data.getLevel());
        if (!guideList.isEmpty()) {
            throw new GuiguException(ResultCodeEnum.TOUR_LEVEL_EXISTS_ERROR);
        }
        tourLevelMapper.update(data);

    }

    @Override
    public List<TourLevel> getAllLevelList() {
        return tourLevelMapper.findAll();
    }
}

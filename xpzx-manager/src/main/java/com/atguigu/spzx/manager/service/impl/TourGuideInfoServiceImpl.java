package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.common.exception.GuiguException;
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
    public PageInfo<TourGuideInfo> findListByPage(Integer page, Integer limit, String name, String nickname, String phone, String remark) {
        PageHelper.startPage(page, limit);
        List<TourGuideInfo> list = tourGuideInfoMapper.findList(name, nickname, phone, remark);
        PageInfo<TourGuideInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void save(TourGuideInfo tourGuideInfo) {
        //判断昵称是否重复
        TourGuideInfo info = tourGuideInfoMapper.findByNickname(tourGuideInfo.getNickname());
        if (info != null && info.getId() != tourGuideInfo.getId()) {
            throw GuiguException.build("昵称重复");
        }
        //判断导游证件号是否重复
        info = tourGuideInfoMapper.findByTourCertificateNo(tourGuideInfo.getTourCertificateNo());
        if (info != null && info.getId() != tourGuideInfo.getId()) {
            throw GuiguException.build("身份证号重复");
        }

        //判断身份证号是否重复
        info = tourGuideInfoMapper.findByIdTypeNo(tourGuideInfo.getIdTypeNo());
        if (info != null && info.getId() != tourGuideInfo.getId()) {
            throw GuiguException.build("身份证号重复");
        }

        tourGuideInfoMapper.save(tourGuideInfo);
    }

    @Override
    public void delete(Integer id) {
        //TODO 判断导游是否正在带团中，如果正在带团中，则不能删除
        tourGuideInfoMapper.delete(id);
    }

    @Override
    public void update(TourGuideInfo tourGuideInfo) {
        tourGuideInfoMapper.update(tourGuideInfo);

    }
}

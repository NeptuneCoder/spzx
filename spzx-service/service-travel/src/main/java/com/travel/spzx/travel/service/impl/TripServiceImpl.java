package com.travel.spzx.travel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travel.spzx.common.exception.GuiguException;
import com.travel.spzx.model.entity.trip.TripInfo;
import com.travel.spzx.model.entity.user.UserInfo;
import com.travel.spzx.travel.mapper.TripInfoMapper;
import com.travel.spzx.travel.service.TripService;
import com.travel.xpzx.utils.AuthContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripServiceImpl implements TripService {
    @Autowired
    private TripInfoMapper tripInfoMapper;

    @Override
    public void addTrip(TripInfo tripInfo) {
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        if (userInfo == null) {
            throw GuiguException.build("请先登录");
        }
        tripInfo.setUserId(userInfo.getId());
        tripInfoMapper.save(tripInfo);
    }

    @Override
    public PageInfo<TripInfo> list(int page, int limit) {
        PageHelper.startPage(page, limit);
        List<TripInfo> tripInfoList = tripInfoMapper.list();

        PageInfo<TripInfo> pageInfo = new PageInfo<>(tripInfoList);
        return pageInfo;
    }

    @Override
    public TripInfo getDetail(String tripId) {
        TripInfo tripInfo = tripInfoMapper.getDetail(tripId);
        return tripInfo;
    }

    @Override
    public void updateTrip(TripInfo tripInfo) {
        tripInfoMapper.update(tripInfo);
    }

    @Override
    public void deleteTrip(String tripId) {
        tripInfoMapper.delete(tripId);
    }
}

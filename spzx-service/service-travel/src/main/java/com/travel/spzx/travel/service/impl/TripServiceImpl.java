package com.travel.spzx.travel.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travel.spzx.common.exception.GuiguException;
import com.travel.spzx.model.entity.trip.TripInfo;
import com.travel.spzx.model.entity.user.UserInfo;
import com.travel.spzx.travel.mapper.TripInfoMapper;
import com.travel.spzx.travel.service.TripService;
import com.travel.xpzx.utils.AuthContextUtil;
import com.travel.xpzx.utils.DateUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        String idCardNo = tripInfo.getIdCardNo();
        int idCardType = tripInfo.getIdCardType();
        if ("0".equals(idCardType)) {
            //根据身份证号码计算用户是否成年,通过正则取出年月日
            //TODO 根据身份证号码计算用户是否成年
            String substring = idCardNo.substring(6, 14);
            //当前时间到身份证出生日期的年月日
            int year = Integer.parseInt(substring.substring(0, 4));
            int month = Integer.parseInt(substring.substring(4, 6));
            int day = Integer.parseInt(substring.substring(6, 8));
            //TODO 根据身份证号码计算用户是否成年
            String birthDay = year + "-" + month + "-" + day;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date birthday = dateFormat.parse(birthDay);
                int age = DateUtils.calculateIsAdult(birthday, new Date());
                String ageType = tripInfo.getAgeType();
                if (age < 18 && ageType.equals("成人")) {
                    throw GuiguException.build("请检查证件号码是否为成年人");
                }

            } catch (ParseException e) {
                throw GuiguException.build("请检查身份证号码格式");
            }
        }
        tripInfoMapper.save(tripInfo);
    }

    @Override
    public PageInfo<TripInfo> list(int page, int limit) {
        PageHelper.startPage(page, limit);
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        List<TripInfo> tripInfoList = tripInfoMapper.list(userInfo.getId());

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

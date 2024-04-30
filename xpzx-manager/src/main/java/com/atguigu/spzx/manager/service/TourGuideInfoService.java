package com.atguigu.spzx.manager.service;


import com.atguigu.spzx.model.entity.guide.TourGuideInfo;
import com.atguigu.spzx.model.vo.common.Result;
import com.github.pagehelper.PageInfo;

public interface TourGuideInfoService {
    PageInfo<TourGuideInfo> findListByPage(Integer page, Integer limit, String name, String nickname, String phone, String remark);

    void save(TourGuideInfo tourGuideInfo);

    void delete(Integer id);

    void update(TourGuideInfo tourGuideInfo);
}

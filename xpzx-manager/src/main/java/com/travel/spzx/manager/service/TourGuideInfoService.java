package com.travel.spzx.manager.service;


import com.travel.spzx.model.entity.guide.TourGuideInfo;
import com.github.pagehelper.PageInfo;

public interface TourGuideInfoService {
    PageInfo<TourGuideInfo> findListByPage(Integer page, Integer limit, String name, String nickname, String phone, String remark);

    void save(TourGuideInfo tourGuideInfo);

    void delete(Integer id);

    void update(TourGuideInfo tourGuideInfo);
}

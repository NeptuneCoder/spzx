package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.guide.TourGuideInfoDto;
import com.atguigu.spzx.model.entity.guide.TourGuideInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TourGuideInfoMapper {
    List<TourGuideInfo> findList();

    void save(TourGuideInfo tourGuideInfo);
}

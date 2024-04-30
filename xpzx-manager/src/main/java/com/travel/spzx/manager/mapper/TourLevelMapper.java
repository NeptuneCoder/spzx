package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.entity.guide.TourLevel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TourLevelMapper {

    void save(TourLevel tourLevel);

    List<TourLevel> findAll();

    void delete(Integer id);

    List<TourLevel> queryByLevel(String level);

    void update(TourLevel data);
}

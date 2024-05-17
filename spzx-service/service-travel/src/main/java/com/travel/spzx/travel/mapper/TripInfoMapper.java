package com.travel.spzx.travel.mapper;

import com.travel.spzx.model.entity.trip.TripInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TripInfoMapper {
    void save(TripInfo tripInfo);

    List<TripInfo> list(@Param("userId") Long userId);

    TripInfo getDetail(String tripId);

    void update(TripInfo tripInfo);

    void delete(String tripId);
}

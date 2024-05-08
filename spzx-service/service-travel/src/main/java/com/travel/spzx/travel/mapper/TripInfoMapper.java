package com.travel.spzx.travel.mapper;

import com.travel.spzx.model.entity.trip.TripInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TripInfoMapper {
    void save(TripInfo tripInfo);

    List<TripInfo> list();

    TripInfo getDetail(String tripId);

    void update(TripInfo tripInfo);

    void delete(String tripId);
}

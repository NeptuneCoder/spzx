package com.travel.spzx.travel.service;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.entity.trip.TripInfo;

public interface TripService {
    void addTrip(TripInfo tripInfo);

    PageInfo<TripInfo> list(int page, int limit);

    TripInfo getDetail(String tripId);

    void updateTrip(TripInfo tripInfo);

    void deleteTrip(String tripId);
}

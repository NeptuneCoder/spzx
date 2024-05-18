package com.travel.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.entity.order.BusTourGuideDto;
import com.travel.spzx.model.entity.order.BusTourGuideInfo;
import com.travel.spzx.model.entity.order.BusTouristDto;
import com.travel.spzx.model.entity.order.BusTouristInfo;
import com.travel.spzx.model.vo.batch.TouristVO;
import com.travel.spzx.model.vo.guide.TourGuideVO;

import java.util.List;

public interface BatchDetailService {
    PageInfo<TouristVO> getTouristList(int page, int limit, Integer batchId, String touristName);

    void touristAssignCar(BusTouristDto busTouristDto);

    List<BusTouristInfo> getAssignedCarTouristList(String carId, String batchId);

    PageInfo<TourGuideVO> getTourGuideList(int page, int limit, String batchId, String tourGuideName);

    void tourGuideAssignCar(BusTourGuideDto busTouristDto);

    List<BusTourGuideInfo> getAssignedCarTourGuideList(String carId, String batchId);
}

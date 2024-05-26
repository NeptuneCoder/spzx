package com.travel.spzx.tour.guide.service;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.vo.batch.GuideBatchDetailVo;
import com.travel.spzx.model.vo.batch.TourGuideVo;
import com.travel.spzx.model.vo.batch.TouristDetailVo;

import java.util.List;

public interface GuideBatchService {
    PageInfo<GuideBatchDetailVo> getLeaderBatchList(Integer page, Integer limit);

    GuideBatchDetailVo getBatchInfo(String batchId);

    PageInfo<TouristDetailVo> getCurrentBatchTripList(Integer page, Integer limit, String type, String batchId, String name, String carId);

    Long touristSign(String batchId, String tripId, String orderId, String orderItemId);

    List<TourGuideVo> getCurrentBatchTourGuideList(String batchId,String carId);

    void tripComplete(String batchId);

    Long touristUnSign(String batchId, String tripId, String orderId, String orderItemId);

}

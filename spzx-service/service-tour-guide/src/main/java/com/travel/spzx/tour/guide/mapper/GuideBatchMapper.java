package com.travel.spzx.tour.guide.mapper;

import com.travel.spzx.model.entity.order.OrderItem;
import com.travel.spzx.model.vo.batch.GuideBatchDetailVo;
import com.travel.spzx.model.vo.batch.TourGuideVo;
import com.travel.spzx.model.vo.batch.TouristDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GuideBatchMapper {
    List<GuideBatchDetailVo> getLeaderBatchList(@Param("tourGuideId") Long userId, @Param("batchId") String batchId);

    List<TouristDetailVo> getCurrentBatchTripList(@Param("checkStatus") Integer checkStatus,
                                                  @Param("batchId") String batchId,
                                                  @Param("name") String name,
                                                  @Param("carId") String carId);

    void touristSign(@Param("batchId") String batchId, @Param("tripId") String tripId, @Param("orderItemId") String orderItemId);

    List<TourGuideVo> getCurrentBatchTourGuideList(@Param("batchId") String batchId, @Param("carId") String carId);
}

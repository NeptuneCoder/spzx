package com.travel.spzx.tour.guide.mapper;

import com.travel.spzx.model.entity.order.OrderInfo;
import com.travel.spzx.model.entity.order.OrderItem;
import com.travel.spzx.model.vo.batch.GuideBatchDetailVo;
import com.travel.spzx.model.vo.batch.TourGuideVo;
import com.travel.spzx.model.vo.batch.TouristDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GuideBatchMapper {
    List<GuideBatchDetailVo> getLeaderBatchList(@Param("tourGuideId") Long userId, @Param("batchId") String batchId);

    List<TouristDetailVo> getCurrentBatchTripList(@Param("checkStatus") Integer checkStatus,
                                                  @Param("batchId") String batchId,
                                                  @Param("name") String name,
                                                  @Param("carId") String carId);


    void touristSign(@Param("orderStatus") int orderStatus,
                     @Param("batchId") String batchId,
                     @Param("tripId") String tripId,
                     @Param("orderId") String orderId,
                     @Param("orderItemId") String orderItemId,
                     @Param("checkStatus") Integer checkStatus);

    List<TourGuideVo> getCurrentBatchTourGuideList(@Param("batchId") String batchId, @Param("carId") String carId);

    List<OrderItem> queryCurrentOrderIsAllSign(@Param("orderId") String orderId, @Param("batchId") String batchId);

    void updateOrderStatus(@Param("orderStatus") Integer orderStatus, @Param("orderId") String orderId);

    List<OrderInfo> queryCurBatchAllOrder(String batchId);
}

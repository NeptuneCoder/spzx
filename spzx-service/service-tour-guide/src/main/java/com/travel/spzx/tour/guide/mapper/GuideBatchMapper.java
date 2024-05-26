package com.travel.spzx.tour.guide.mapper;

import com.travel.spzx.model.entity.order.OrderInfo;
import com.travel.spzx.model.entity.order.OrderItem;
import com.travel.spzx.model.vo.batch.GuideBatchDetailVo;
import com.travel.spzx.model.vo.batch.TourGuideVo;
import com.travel.spzx.model.vo.batch.TouristDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.Collection;
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

    /**
     * 查询当前批次已支付，已签到，未签到，部分签到的所有订单(1, 3, 4,16)
     *
     * @param orderId
     * @param batchId
     * @return
     */
    List<OrderItem> queryCurrentOrderItemIsAllSign(@Param("orderId") String orderId, @Param("batchId") String batchId);

    void updateOrderStatus(@Param("orderStatus") Integer orderStatus, @Param("orderId") String orderId);

    List<OrderInfo> queryCurBatchAllOrder(String batchId);

    List<String> queryCurBatchCars(String batchId);

    Collection<OrderItem> getOrderItemByOrderId(Long id);

    /**
     * 批次停止报名
     *
     * @param batchId
     * @param status
     */
    void batchStopApply(@Param("batchId") String batchId, @Param("status") int status);


    int getCurBatchStatus(@Param("batchId") String batchId);

    List<OrderItem> queryCurOrderAllItem(@Param("orderId") String orderId, @Param("batchId") String batchId);

    void travelFinished(@Param("orderId") Long orderId, @Param("orderStatus") Integer nextOrderStatus, @Param("commentStatus") Integer commentStatus, @Param("refundStatus") Integer refundStatus, @Param("refundAmount") BigDecimal refundAmount);

    List<OrderItem> queryCurBatchPayedOrder(String batchId);
}

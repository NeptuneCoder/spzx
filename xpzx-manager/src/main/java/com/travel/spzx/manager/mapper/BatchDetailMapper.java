package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.entity.order.BusTourGuideInfo;
import com.travel.spzx.model.entity.order.BusTouristInfo;
import com.travel.spzx.model.vo.batch.TouristVO;
import com.travel.spzx.model.vo.guide.TourGuideVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BatchDetailMapper {
    /**
     * 查询当前批次有哪些报名成功且是否分配车辆信息
     *
     * @param batchId
     * @param touristName
     * @return
     */
    List<TouristVO> querySuccessTouristByBatchId(@Param("batchId") Integer batchId,
                                                 @Param("touristName") String touristName);

    /**
     * 删除当前批次用户分配的车辆信息
     *
     * @param carId
     * @param batchId
     */
    void deleteBusTouristGuide(@Param("carId") String carId,
                               @Param("batchId") String batchId);

    void saveBusTouristGuide(BusTourGuideInfo busTourGuideInfo);


    void deleteBusTourist(@Param("carId") String carId,
                          @Param("batchId") String batchId);

    void saveBusTourist(BusTouristInfo busTouristInfo);

    List<BusTouristInfo> queryAssignedCarTourist(@Param("carId") String carId, @Param("batchId") String batchId);

    /**
     * 查询车辆对应的导游数量
     *
     * @param carId
     * @param batchId
     * @return
     */
    int queryTourGuideNumByCarAndBatchId(@Param("carId") String carId, @Param("batchId") String batchId);

    /**
     * 删除该游客分配给其他车的记录信息
     *
     * @param touristId
     * @param carId
     * @param batchId
     */
    void delAssignOhterBusTourist(@Param("tourId") String touristId,
                                  @Param("carId") String carId,
                                  @Param("batchId") String batchId);

    List<TourGuideVO> queryTourGuideList(@Param("batchId") String batchId, @Param("tourGuideName") String tourGuideName);

    int queryTouristNumByCarAndBatchId(@Param("carId") String carId, @Param("batchId") String batchId);

    List<BusTourGuideInfo> queryAssignedCarTourGuide(@Param("carId") String carId, @Param("batchId") String batchId);

    void delAssignOtherBusTourGuide(@Param("tourGuideId") String tourGuideId,
                                  @Param("carId") String carId,
                                  @Param("batchId") String batchId);

    void deleteBusTourGuide(@Param("carId") String carId,
                          @Param("batchId") String batchId);

    void saveBusTourGuide(BusTourGuideInfo busTouristInfo);
}

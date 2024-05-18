package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.entity.bus.BusInfo;
import com.travel.spzx.model.entity.order.BatchBusInfo;
import com.travel.spzx.model.entity.order.BatchBusInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BatchBusMapper {
    List<BatchBusInfo> findByBatchId(@Param("batchId") String batchId);

    void insertSelective(BatchBusInfo busInfo);

    /**
     * 删除某个批次的车辆信息
     *
     * @param id
     * @param batchId
     */
    void deleteByPrimaryKey(@Param("id") String id, @Param("batchId") String batchId);

    void updateByPrimaryKeySelective(BatchBusInfo busInfo);


    void deleteByBatchId(@Param("batchId") String batchId);

    int queryTourGuideNumByCarIdBatchId(@Param("carId") String carId, @Param("batchId") String batchId);

    int queryTouristNumByCarIdBatchId(@Param("carId") String carId, @Param("batchId") String batchId);


}

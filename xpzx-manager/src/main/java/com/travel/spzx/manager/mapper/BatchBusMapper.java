package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.entity.bus.BusInfo;
import com.travel.spzx.model.entity.order.BatchBusInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BatchBusMapper {
    List<BatchBusInfo> findByPage(@Param("carNo") String carNo, @Param("driverName") String driverName);

    void insertSelective(BatchBusInfo busInfo);

    void deleteByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(BatchBusInfo busInfo);

    List<BatchBusInfo> findAll();

}

package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.entity.bus.BusInfo;
import com.travel.spzx.model.entity.order.BusTourGuideInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BusTourGuideMapper {
    List<BusTourGuideInfo> findByPage(@Param("carNo") String carNo, @Param("driverName") String driverName);

    void insertSelective(BusTourGuideInfo busInfo);

    void deleteByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(BusTourGuideInfo busInfo);

    List<BusTourGuideInfo> findAll();

}

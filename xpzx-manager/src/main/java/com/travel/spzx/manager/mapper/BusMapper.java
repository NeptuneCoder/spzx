package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.entity.bus.BusInfo;
import com.travel.spzx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BusMapper {
    List<BusInfo> findByPage(@Param("carNo") String carNo, @Param("driverName") String driverName);

    void insertSelective(BusInfo busInfo);

    void deleteByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(BusInfo busInfo);

    List<BusInfo> findAll();

}

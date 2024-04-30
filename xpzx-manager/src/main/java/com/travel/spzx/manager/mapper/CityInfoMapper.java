package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.entity.city.CityInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CityInfoMapper {
    List<CityInfo> findByPage();

    void save(CityInfo cityInfo);

    void delete(Integer id);

    void update(CityInfo cityInfo);


    List<CityInfo> findByCityName(@Param("cityName") String cityName);
}

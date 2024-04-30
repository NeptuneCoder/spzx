package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrandMapper {
    List<Brand> findByPage();

    void insertSelective(Brand brand);

    void deleteByPrimaryKey(Integer id);

    void updateByPrimaryKeySelective(Brand brand);

    List<Brand> findAll();

}

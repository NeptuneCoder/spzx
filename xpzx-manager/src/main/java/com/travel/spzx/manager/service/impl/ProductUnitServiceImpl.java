package com.travel.spzx.manager.service.impl;

import com.travel.spzx.manager.mapper.ProductUnitMapper;
import com.travel.spzx.manager.service.ProductUnitService;
import com.travel.spzx.model.entity.base.ProductUnit;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductUnitServiceImpl implements ProductUnitService {
    @Mapper
    ProductUnitMapper productUnitMapper;

    @Override
    public List<ProductUnit> findAll() {
        return productUnitMapper.findAll();
    }
}

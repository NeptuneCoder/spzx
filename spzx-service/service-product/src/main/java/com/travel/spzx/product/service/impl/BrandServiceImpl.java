package com.travel.spzx.product.service.impl;


import com.travel.spzx.model.entity.product.Brand;
import com.travel.spzx.product.mapper.BrandMapper;
import com.travel.spzx.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandMapper brandMapper;

    @Cacheable(value = "brandList", unless = "#result.size() == 0")
    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }
}

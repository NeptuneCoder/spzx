package com.travel.spzx.manager.service;

import com.travel.spzx.model.entity.product.Brand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface BrandService {
    PageInfo<Brand> findByPage(Integer page, Integer limit);

    void save(Brand brand);

    void delete(Integer id);

    void update(Brand brand);

    List<Brand> findAll();

}

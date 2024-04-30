package com.travel.spzx.manager.service;

import com.travel.spzx.model.dto.product.CategoryBrandDto;
import com.travel.spzx.model.entity.product.Brand;
import com.travel.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CategoryBrandService {
    PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto);

    void save(CategoryBrand categoryBrand);

    List<Brand> findBrandByCategoryId(Long categoryId);
}

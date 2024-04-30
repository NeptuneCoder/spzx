package com.travel.spzx.manager.service.impl;

import com.travel.spzx.manager.mapper.CategoryBrandMapper;
import com.travel.spzx.manager.service.CategoryBrandService;
import com.travel.spzx.model.dto.product.CategoryBrandDto;
import com.travel.spzx.model.entity.product.Brand;
import com.travel.spzx.model.entity.product.CategoryBrand;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {
    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto) {
        PageHelper.startPage(page, limit);
        return new PageInfo<CategoryBrand>(categoryBrandMapper.findByPage(categoryBrandDto));

    }

    @Override
    public void save(CategoryBrand categoryBrand) {
        categoryBrandMapper.save(categoryBrand);
    }

    @Override
    public List<Brand> findBrandByCategoryId(Long categoryId) {
        return categoryBrandMapper.findBrandByCategoryId(categoryId);
    }
}

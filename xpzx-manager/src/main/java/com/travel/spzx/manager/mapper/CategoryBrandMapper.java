package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.dto.product.CategoryBrandDto;
import com.travel.spzx.model.entity.product.Brand;
import com.travel.spzx.model.entity.product.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryBrandMapper {
    List<CategoryBrand> findByPage(CategoryBrandDto categoryBrandDto);

    void save(CategoryBrand categoryBrand);

    List<Brand> findBrandByCategoryId(Long categoryId);
}

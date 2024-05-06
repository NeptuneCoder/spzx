package com.travel.spzx.travel.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.travel.spzx.model.dto.product.BannerDto;
import com.travel.spzx.model.dto.product.CategoryDto;
import com.travel.spzx.model.dto.product.IndexDto;
import com.travel.spzx.model.entity.product.Category;
import com.travel.spzx.model.entity.product.Product;
import com.travel.spzx.travel.mapper.IndexMapper;
import com.travel.spzx.travel.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private IndexMapper indexMapper;

    @Override
    public IndexDto index() {
        //查询推荐商品;创建商品时设置是否推荐选项;
        List<Product> byPage = indexMapper.findByPage();
        List<BannerDto> bannerList = new ArrayList<>();
        byPage.forEach(product -> {
            BannerDto bannerDto = new BannerDto();
            String sliderUrls = product.getSliderUrls();
            if (!StrUtil.isEmpty(sliderUrls)) {
                if (sliderUrls.contains(",")) {
                    String[] urls = sliderUrls.split(",");
                    bannerDto.setImgUrl(urls[0]);
                } else {
                    bannerDto.setImgUrl(sliderUrls);
                }
            }
            bannerDto.setId(String.valueOf(product.getId()));
            bannerList.add(bannerDto);
        });

        List<Category> category = indexMapper.findCategory("0");
        List<CategoryDto> oneCategoryList = category.stream().map(sub -> {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(sub.getName());
            categoryDto.setCategoryId(String.valueOf(sub.getId()));
            categoryDto.setIcon(sub.getImageUrl());
            List<Category> subCategory = indexMapper.findCategory(String.valueOf(sub.getId()));
            List<CategoryDto> childcategoryDtos = subCategory.stream().map(childSub -> {
                System.out.println("toJSONString == " + JSON.toJSONString(childSub));
                CategoryDto childCategoryDto = new CategoryDto();
                childCategoryDto.setName(childSub.getName());
                childCategoryDto.setCategoryId(String.valueOf(childSub.getId()));
                childCategoryDto.setIcon(childSub.getImageUrl());
                return childCategoryDto;
            }).collect(Collectors.toList());
            categoryDto.setChildren(childcategoryDtos);
            return categoryDto;
        }).collect(Collectors.toList());


        IndexDto indexDto = new IndexDto();
        indexDto.setBannerList(bannerList);
        indexDto.setOneCategoryList(oneCategoryList);
        //查询商品分类，根据耳机分类查询商品;
        return indexDto;
    }
}

package com.travel.spzx.product.service;

import com.travel.spzx.model.entity.product.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findOneCategory();

    List<Category> findCategoryTree();

}

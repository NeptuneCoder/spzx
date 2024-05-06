package com.travel.spzx.travel.service;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.dto.product.ProductDto;
import com.travel.spzx.model.entity.product.Product;

public interface ProductService {
    PageInfo<Product> getProductList(int page, int limit, ProductDto productDto);

    Product getProductDetail(Long id,Long category2Id);
}

package com.atguigu.spzx.cart.service;

import com.atguigu.spzx.model.entity.product.ProductSku;

public interface ProductService {
    ProductSku getBySkuId(Long skuId);
}

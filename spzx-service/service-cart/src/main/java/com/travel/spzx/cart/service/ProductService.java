package com.travel.spzx.cart.service;

import com.travel.spzx.model.entity.product.ProductSku;

public interface ProductService {
    ProductSku getBySkuId(Long skuId);
}

package com.travel.spzx.product.mapper;

import com.travel.spzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailsMapper {
    ProductDetails getByProductId(Long productId);
}

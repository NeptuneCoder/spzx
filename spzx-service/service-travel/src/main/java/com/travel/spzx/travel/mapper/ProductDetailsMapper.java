package com.travel.spzx.travel.mapper;

import com.travel.spzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDetailsMapper {


    ProductDetails selectByProductId(Long id);

}

package com.travel.spzx.cart.mapper;

import com.travel.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductSkuMapper {


    ProductSku getById(Long skuId);


}

package com.travel.spzx.travel.mapper;

import com.travel.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    
    List<ProductSku> selectByProductId(Long id);

}

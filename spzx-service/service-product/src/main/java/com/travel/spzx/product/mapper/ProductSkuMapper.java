package com.travel.spzx.product.mapper;

import com.travel.spzx.model.dto.h5.ProductSkuDto;
import com.travel.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    List<ProductSku> findProductSkuBySale();

    List<ProductSku> findByPage(ProductSkuDto productSkuDto);

    ProductSku getById(Long skuId);

    List<ProductSku> findByProductId(Long productId);

    void updateSale(@Param("skuId") Long skuId, @Param("num") Integer num);
}

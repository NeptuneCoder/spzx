package com.travel.spzx.product.service;

import com.travel.spzx.model.dto.h5.ProductSkuDto;
import com.travel.spzx.model.dto.product.SkuSaleDto;
import com.travel.spzx.model.entity.product.ProductSku;
import com.travel.spzx.model.vo.h5.ProductItemVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductService {
    List<ProductSku> findProductSkuBySale();

    PageInfo<ProductSku> findByPage(Integer page, Integer limit, ProductSkuDto productSkuDto);

    ProductItemVo item(Long skuId);

    ProductSku getBySkuId(Long skuId);

    Boolean updateSkuSaleNum(List<SkuSaleDto> skuSaleDtoList);
}

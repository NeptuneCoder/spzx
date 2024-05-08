package com.travel.spzx.travel.service;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.dto.product.ProductDto;
import com.travel.spzx.model.entity.product.Product;
import com.travel.spzx.model.vo.product.OrderProductInfoVo;

public interface ProductService {
    PageInfo<Product> getProductList(int page, int limit, ProductDto productDto);

    Product getProductDetail(Long id,Long category2Id);

    OrderProductInfoVo getDetailByProductIdAndBatchId(Long id, Long batchId);

}

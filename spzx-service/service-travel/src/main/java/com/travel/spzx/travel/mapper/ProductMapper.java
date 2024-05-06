package com.travel.spzx.travel.mapper;

import com.travel.spzx.model.dto.product.ProductDto;
import com.travel.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {



    List<Product> findByPage(ProductDto productDto);

    Product selectById(Long id);
}

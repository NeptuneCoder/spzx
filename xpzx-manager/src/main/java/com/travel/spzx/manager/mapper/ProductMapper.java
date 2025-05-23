package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.dto.product.ProductDto;
import com.travel.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> findByPage(ProductDto productDto);

    int save(Product product);

    Product selectById(Long id);

    void updateById(Product product);

    void deleteById(Long id);

    List<Product> findAll();
}

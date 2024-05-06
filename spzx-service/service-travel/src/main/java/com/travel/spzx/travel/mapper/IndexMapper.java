package com.travel.spzx.travel.mapper;

import com.travel.spzx.model.dto.product.BannerDto;
import com.travel.spzx.model.entity.product.Category;
import com.travel.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IndexMapper {
    /**
     * 查询首页推荐的商品
     *
     * @return
     */
    List<Product> findByPage();

    List<Category> findCategory(@Param("parentId") String parentId);
}

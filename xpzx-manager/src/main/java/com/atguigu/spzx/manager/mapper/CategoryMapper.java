package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface CategoryMapper {

    List<Category> selectByParentId(Long parentId);

    Integer countByParentId(Long id);


    List<Category> selectAll();

    void batchInsert(@Param("list") List list);
}

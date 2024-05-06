package com.travel.spzx.travel.mapper;

import com.travel.spzx.model.entity.product.BatchItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BatchInfoMapper {


    List<BatchItem> selectByProductId(Long id);

    List<BatchItem> queryByProductIdLimitOne(Long id);

}

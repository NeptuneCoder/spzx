package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.entity.product.BatchItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BatchInfoMapper {
    void save(BatchItem v);

    List<BatchItem> selectByProductId(@Param("productId") Long id);

    void updateById(BatchItem v);

    List<BatchItem> findAll(@Param("productId") String productId);

    BatchItem getDetailByBatchId(@Param("id") String batchId);
}

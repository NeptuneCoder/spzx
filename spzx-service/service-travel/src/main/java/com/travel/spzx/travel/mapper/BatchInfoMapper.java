package com.travel.spzx.travel.mapper;

import com.travel.spzx.model.entity.product.BatchItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface BatchInfoMapper {


    List<BatchItem> selectByProductId(Long id);

    BatchItem selectByBatchId(@Param("id") Long id);


    List<BatchItem> selectByProductIdAndBatchId(Long productId, Long batchId);

    List<BatchItem> queryByProductIdLimitOne(Long id);

    void updateSaleNum(@Param("batchId") Long id, @Param("saleNum") int num, @Param("cancelDate") Date cancelDate);

    void updateSaleNum(@Param("batchId") Long id, @Param("saleNum") int num);
}

package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.entity.product.BatchItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BatchInfoMapper {
    void save(BatchItem v);

    List<BatchItem> selectByProductId(Long id);

    void updateById(BatchItem v);
}

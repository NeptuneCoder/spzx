package com.travel.spzx.travel.mapper;

import com.travel.spzx.model.entity.collect.Collect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CollectMapper {

    List<Collect> queryCollectList();

    Collect queryCollect(@Param("userId") Long userId, @Param("productId") Long productId);

    void insertCollect(Collect collect);

    void updateCollect(@Param("userId") Long userId, @Param("productId") Long productId, @Param("status") int i);
}

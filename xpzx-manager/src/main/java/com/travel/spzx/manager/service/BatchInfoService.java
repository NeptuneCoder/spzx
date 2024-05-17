package com.travel.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.entity.product.BatchItem;
import com.travel.spzx.model.vo.product.BatchInfoVo;

import java.util.List;

public interface BatchInfoService {
    List<BatchItem> getBatchInfoByProductId(Long productId);

    PageInfo<BatchInfoVo> list(Integer page, Integer limit);

    List<BatchItem> findAll(String productId);

    BatchItem getDetailByBatchId(String batchId);
}

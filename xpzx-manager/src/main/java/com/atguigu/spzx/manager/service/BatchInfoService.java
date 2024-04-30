package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.BatchItem;

import java.util.List;

public interface BatchInfoService {
    List<BatchItem> getBatchInfoByProductId(Long productId);
}

package com.travel.spzx.manager.service;

import com.travel.spzx.model.entity.product.BatchItem;

import java.util.List;

public interface BatchInfoService {
    List<BatchItem> getBatchInfoByProductId(Long productId);
}

package com.travel.spzx.travel.service;

import com.travel.spzx.model.entity.product.BatchItem;

import java.util.List;

public interface BatchService {


    BatchItem getBatchItem(Long productId, Long batchId);

    List<BatchItem> queryProductBatch(Long productId);

    List<BatchItem> queryLatestBatch(Long id);
}

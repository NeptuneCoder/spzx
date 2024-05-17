package com.travel.spzx.travel.service.impl;

import com.travel.spzx.model.entity.product.BatchItem;
import com.travel.spzx.travel.mapper.BatchInfoMapper;
import com.travel.spzx.travel.service.BatchService;
import com.travel.xpzx.utils.BatchUtils;
import com.travel.xpzx.utils.DateUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("batchService")
public class BatchServiceImpl implements BatchService {


    @Autowired
    private BatchInfoMapper batchInfoMapper;

    @Override
    public BatchItem getBatchItem(Long id, Long batchId) {
        List<BatchItem> batchItems = batchInfoMapper.selectByProductIdAndBatchId(id, batchId);
        batchItems.forEach(v -> {
            BatchUtils.computeBaseInfo(v, v.getTime());
            v.setDuration(BatchUtils.computeDuration(v.getTime()));
            BatchUtils.computeTripState(v);
        });
        return batchItems.get(0);
    }

    @Override
    public List<BatchItem> queryProductBatch(Long productId) {

        return batchInfoMapper.selectByProductId(productId)
                .stream()
                .map(v -> {
                    //TODO 根据批次id查询订单已经成交的数量
                    String[] timeArray = v.getTime();
                    if (timeArray.length == 2) {
                        BatchUtils.computeBaseInfo(v, timeArray);
                        v.setDuration(BatchUtils.computeDuration(v.getTime()));
                    }
                    BatchUtils.computeTripState(v);
                    return v;
                }).collect(Collectors.toList());

    }

    @Override
    public List<BatchItem> queryLatestBatch(Long id) {
        List<BatchItem> batchItems = batchInfoMapper.queryByProductIdLimitOne(id);
        batchItems.forEach(v -> {
            BatchUtils.computeBaseInfo(v, v.getTime());
            v.setDuration(BatchUtils.computeDuration(v.getTime()));
            BatchUtils.computeTripState(v);
        });
        return batchItems;
    }



}

package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.manager.mapper.BatchInfoMapper;
import com.atguigu.spzx.manager.service.BatchInfoService;
import com.atguigu.spzx.model.entity.product.BatchItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchInfoServiceImpl implements BatchInfoService {

    @Autowired
    private BatchInfoMapper batchInfoMapper;

    @Override
    public List<BatchItem> getBatchInfoByProductId(Long productId) {

        return batchInfoMapper.selectByProductId(productId);
    }
}

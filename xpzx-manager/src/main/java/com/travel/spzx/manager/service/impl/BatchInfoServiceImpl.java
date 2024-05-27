package com.travel.spzx.manager.service.impl;

import cn.hutool.db.PageResult;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travel.spzx.manager.mapper.BatchInfoMapper;
import com.travel.spzx.manager.mapper.ProductMapper;
import com.travel.spzx.manager.service.BatchInfoService;
import com.travel.spzx.model.dto.product.ProductDto;
import com.travel.spzx.model.entity.product.BatchItem;
import com.travel.spzx.model.entity.product.Product;
import com.travel.spzx.model.vo.product.BatchInfoVo;
import com.travel.xpzx.utils.BatchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class BatchInfoServiceImpl implements BatchInfoService {

    @Autowired
    private BatchInfoMapper batchInfoMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public PageInfo<BatchItem> getBatchInfoByProductId(Long productId, int page, int limit) {
        PageHelper.startPage(page, limit);
        List<BatchItem> batchItems = batchInfoMapper.selectByProductId(productId);
        batchItems.forEach(item -> {
            if (item.getStatus() == 0) {
                BatchUtils.computeTripState(item);
            } else if (item.getStatus() == 1) {
                item.setStatusStr("行程已结束");
            } else {
                item.setStatusStr("新状态，待开发");
            }
            item.setDuration(BatchUtils.computeDuration(item.getTime()));
        });
        return new PageInfo<>(batchItems);
    }

    @Override
    public PageInfo<BatchInfoVo> list(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        ProductDto productDto = new ProductDto();
        List<Product> res = productMapper.findByPage(productDto);
        ArrayList<BatchInfoVo> resList = new ArrayList<>();
        res.forEach(product -> {
            BatchInfoVo batchInfoVo = new BatchInfoVo();
            batchInfoVo.setId(product.getId());
            batchInfoVo.setProductName(product.getName());
            List<BatchItem> batchItems = batchInfoMapper.selectByProductId(product.getId());

            batchItems.forEach(batchItem -> {
                System.out.println("batchItems JSON:" + product.getName() + " startTime:" + batchItem.getStartTime() + " endTime:" + batchItem.getEndTime());
                BatchUtils.computeBaseInfo(batchItem, batchItem.getTime());
                batchItem.setDuration(BatchUtils.computeDuration(batchItem.getTime()));
                BatchUtils.computeTripState(batchItem);
                batchItem.setProductName(batchItem.getDuration());
            });
            batchInfoVo.setChildren(batchItems);
            resList.add(batchInfoVo);
        });
        return new PageInfo<>(resList);
    }

    @Override
    public List<BatchItem> findAll(String productId) {
        List<BatchItem> all = batchInfoMapper.findAll(productId);
        all.forEach(item -> {
            BatchUtils.computeTripState(item);
            BatchUtils.computeBaseInfo(item, item.getTime());
            item.setDuration(BatchUtils.computeDuration(item.getTime()));
        });
        return all;
    }

    @Override
    public BatchItem getDetailByBatchId(String batchId) {
        BatchItem batchItem = batchInfoMapper.getDetailByBatchId(batchId);
        BatchUtils.computeTripState(batchItem);
        BatchUtils.computeBaseInfo(batchItem, batchItem.getTime());
        batchItem.setDuration(BatchUtils.computeDuration(batchItem.getTime()));
        return batchItem;
    }

    private List<BatchItem> parseBatchItems(List<BatchItem> batchItems) {
        Map<String, List<BatchItem>> map = new HashMap<>();

        batchItems.forEach(item -> {
            map.computeIfAbsent(item.getProductName(), k -> new ArrayList<>());
            map.get(item.getProductName()).add(item);
        });

        ArrayList<BatchItem> res = new ArrayList<>();
        map.forEach((k, v) -> {
            BatchItem batchItem = new BatchItem();
            batchItem.setProductName(k);
            batchItem.setChildren(v);
            double random = Math.random();

            res.add(batchItem);
        });
        return res;
    }

}

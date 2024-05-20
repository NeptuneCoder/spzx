package com.travel.spzx.travel.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.dto.product.ProductDto;
import com.travel.spzx.model.entity.product.BatchItem;
import com.travel.spzx.model.entity.product.Product;
import com.travel.spzx.model.entity.product.ProductDetails;
import com.travel.spzx.model.entity.product.ProductSku;
import com.travel.spzx.model.vo.product.OrderProductInfoVo;
import com.travel.spzx.model.vo.protocol.ProtocolVo;
import com.travel.spzx.travel.mapper.ProductDetailsMapper;
import com.travel.spzx.travel.mapper.ProductMapper;
import com.travel.spzx.travel.mapper.ProductSkuMapper;
import com.travel.spzx.travel.service.BatchService;
import com.travel.spzx.travel.service.ProductService;
import com.travel.spzx.travel.service.ProtocolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;


    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    @Autowired
    private ProtocolService protocolService;

    @Autowired
    private BatchService batchService;

    @Override
    public PageInfo<Product> getProductList(int page, int limit, ProductDto productDto) {
        // TODO Auto-generated method stub
        PageHelper.startPage(page, limit);
        List<Product> byPage = productMapper.findByPage(productDto);
        List<Product> collect = byPage.stream()
                .map(v -> {
                    v.parserImage();
                    List<ProductSku> productSkus = productSkuMapper.selectByProductId(v.getId());
                    v.setProductSkuList(productSkus);
                    List<BatchItem> batchItems = batchService.queryLatestBatch(v.getId());
                    //查询最近批次的时长
                    v.setBatchInfo(batchItems);
                    String resultTime = "";
                    if (!batchItems.isEmpty()) {
                        resultTime = batchItems.stream()
                                .findFirst().get().getDuration();
                    }

                    String feature = v.getFeature();
                    v.setFeature(StrUtil.isEmpty(feature) ? resultTime : (feature + "," + resultTime));

                    return v;
                }).filter(v -> !v.getBatchInfo().isEmpty())//过滤只有一个批次为0的商品
                .collect(Collectors.toList());
        //根据商品id查询规格信息

        return new PageInfo(collect);
    }


    @Override
    public Product getProductDetail(Long id, Long category2Id) {
        Product product = productMapper.selectById(id);
        product.parserImage();

        List<ProductSku> productSkus = productSkuMapper.selectByProductId(id);
        product.setProductSkuList(productSkus);
        //当前日期插入，选择开始日期小于今天的批次

        List<BatchItem> batchItems = batchService.queryProductBatch(id);
        product.setBatchInfo(batchItems);

        String resultTime = batchItems.stream()
                .findFirst().get().getDuration();
        String feature = product.getFeature();
        System.out.println("feature = " + feature);
        product.setFeature(StrUtil.isEmpty(feature) ? resultTime : (feature + "," + resultTime));

        ProductDetails productDetails = productDetailsMapper.selectByProductId(id);
        product.setDetailsImageUrls(productDetails.getImageUrls());
        ProductDto productDto = new ProductDto();
        productDto.setCategory2Id(category2Id);
        PageInfo<Product> productList = getProductList(1, 4, productDto);
        product.setSimilarProducts(productList.getList());

        return product;
    }


    @Override
    public OrderProductInfoVo getDetailByProductIdAndBatchId(Long id, Long batchId) {
        OrderProductInfoVo orderProductInfoVo = new OrderProductInfoVo();

        Product product = productMapper.selectById(id);
        orderProductInfoVo.setProductId(String.valueOf(product.getId()));
        orderProductInfoVo.setName(product.getName());
        orderProductInfoVo.setFeature(product.getFeature());

        List<ProductSku> productSkus = productSkuMapper.selectByProductId(id);
        orderProductInfoVo.setAdultPrice(productSkus.get(0).getSalePrice());
        orderProductInfoVo.setChildPrice(productSkus.get(0).getChildSalePrice());

        BatchItem batchItem = batchService.getBatchItem(id, batchId);
        if (batchItem != null) {
            orderProductInfoVo.setStartDate(batchItem.getStartDate());
            orderProductInfoVo.setEndDate(batchItem.getEndDate());
            orderProductInfoVo.setStartWeak(batchItem.getStartWeak());
            orderProductInfoVo.setEndWeak(batchItem.getEndWeak());
            orderProductInfoVo.setDuration(batchItem.getDuration());
            Integer res = batchItem.getTotalNum() - batchItem.getSaleNum();
            orderProductInfoVo.setSurplusNum(res);
        }

        orderProductInfoVo.setAssembleAddress(product.getStartCityName() + "/" + product.getStartAddress());
        orderProductInfoVo.setAssembleTime(product.getAssembleTime());


        //TODO 查询协议信息
        List<ProtocolVo> purchase = protocolService.getProtocolData("purchase");
        orderProductInfoVo.setProtocolVoList(purchase);
        System.out.println("purchase = " + JSON.toJSONString(purchase));
        return orderProductInfoVo;
    }


}

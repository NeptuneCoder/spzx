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
import com.travel.spzx.travel.mapper.BatchInfoMapper;
import com.travel.spzx.travel.mapper.ProductDetailsMapper;
import com.travel.spzx.travel.mapper.ProductMapper;
import com.travel.spzx.travel.mapper.ProductSkuMapper;
import com.travel.spzx.travel.service.ProductService;
import com.travel.xpzx.utils.DateUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;

    @Autowired
    private BatchInfoMapper batchInfoMapper;

    @Autowired
    private ProductDetailsMapper productDetailsMapper;

    @Override
    public PageInfo<Product> getProductList(int page, int limit, ProductDto productDto) {
        // TODO Auto-generated method stub
        PageHelper.startPage(page, limit);
        List<Product> byPage = productMapper.findByPage(productDto);
        List<Product> collect = byPage.stream()
                .map(v -> {
                    parserImage(v);
                    List<ProductSku> productSkus = productSkuMapper.selectByProductId(v.getId());
                    v.setProductSkuList(productSkus);
                    //查询最近批次的时长
                    List<BatchItem> batchItems = batchInfoMapper.queryByProductIdLimitOne(v.getId());
                    v.setBatchInfo(batchItems);
                    System.out.println("v.getBatchInfo = " + v.getName() + " productId== " + v.getId() + "  批次信息：" + JSON.toJSONString(v.getBatchInfo()));
                    String resultTime = batchItems.stream()
                            .findFirst()
                            .stream()
                            .map(BatchItem::getTime)
                            .filter(time -> time.length == 2)
                            .map(strings -> computeDuration(strings)).collect(Collectors.joining(", "));
                    String feature = v.getFeature();
                    v.setFeature(StrUtil.isEmpty(feature) ? resultTime : (feature + "," + resultTime));

                    return v;
                }).filter(v -> {
                    // && (v.getBatchInfo().get(0).getStartTime().compareTo(new Date()) > 0) 过滤掉开始时间
                    System.out.println("v.getBatchInfo.isEmpty = " + v.getBatchInfo().isEmpty());
                    return !v.getBatchInfo().isEmpty();
                })//过滤只有一个批次为0的商品
                .collect(Collectors.toList());
        //根据商品id查询规格信息


        return new PageInfo(collect);
    }

    @NotNull
    private static String computeDuration(String[] strings) {
        String[] startTimeSplit = strings[0].split("-");
        LocalDate date1 = LocalDate.of(Integer.valueOf(startTimeSplit[0]), Integer.valueOf(startTimeSplit[1]), Integer.valueOf(startTimeSplit[2]));
        String[] endTimeSplit = strings[1].split("-");
        LocalDate date2 = LocalDate.of(Integer.valueOf(endTimeSplit[0]), Integer.valueOf(endTimeSplit[1]), Integer.valueOf(endTimeSplit[2]));
        long between = ChronoUnit.DAYS.between(date1, date2) + 1;
        return between == 1 ? "1天" : between + "天" + (between - 1) + "夜";
    }

    @Override
    public Product getProductDetail(Long id, Long category2Id) {
        Product product = productMapper.selectById(id);
        parserImage(product);

        List<ProductSku> productSkus = productSkuMapper.selectByProductId(id);
        product.setProductSkuList(productSkus);
        //当前日期插入，选择开始日期小于今天的批次

        List<BatchItem> batchItems = batchInfoMapper.selectByProductId(id)
                .stream()
                .map(v -> {
                    //TODO 根据批次id查询订单已经成交的数量

                    String[] timeArray = v.getTime();
                    if (timeArray.length == 2) {
                        computeBaseInfo(v, timeArray);
                        v.setDuration(computeDuration(v.getTime()));
                    }
                    computeTripState(v);
                    return v;
                }).collect(Collectors.toList());
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

    private static void computeTripState(BatchItem v) {
        // TODO 已支付完成的报名人数
        int registeredNum = 0;
        v.setSaleNum(registeredNum);
        //TODO 处理成团状态
        Integer totalNum = Integer.valueOf(v.getTotalNum());
        Integer successNum = Integer.valueOf(v.getSuccessNum());
        //0:已成团 1:可报名 2:已满员
        if (registeredNum == totalNum) {
            v.setTripStatus(2);
            //已满员
        } else if (registeredNum > successNum) {
            v.setTripStatus(0);
            //已成团
        } else if (registeredNum < successNum) {
            //可以报名
            v.setTripStatus(1);
        }
    }

    private static void computeBaseInfo(BatchItem v, String[] timeArray) {
        String startDate = timeArray[0];
        String startWeek = DateUtils.getCurrentWeek(startDate);
        String[] startDateArray = startDate.split("-");
        String[] endDateArray = timeArray[1].split("-");
        String res = startDateArray[1] + "/" + startDateArray[2] + "(" + startWeek + ")-" + endDateArray[1] + "/" + endDateArray[2];
        v.setDateStr(res);
        v.setStartDate(DateUtils.string2Date(startDate));
        v.setStartWeak(startWeek);
        String endDate = timeArray[1];
        String endWeek = DateUtils.getCurrentWeek(endDate);
        v.setEndDate(DateUtils.string2Date(endDate));
        v.setEndWeak(endWeek);
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
        orderProductInfoVo.setChildPrice(productSkus.get(0).getSalePrice());

        List<BatchItem> batchItems = batchInfoMapper.selectByProductIdAndBatchId(id, batchId);
        batchItems.forEach(v -> {
            computeBaseInfo(v, v.getTime());
            v.setDuration(computeDuration(v.getTime()));
            computeTripState(v);
        });
        if (!batchItems.isEmpty()) {
            BatchItem batchItem = batchItems.get(0);
            orderProductInfoVo.setStartDate(batchItem.getStartDate());
            orderProductInfoVo.setEndDate(batchItem.getEndDate());
            orderProductInfoVo.setStartWeak(batchItem.getStartWeak());
            orderProductInfoVo.setEndWeak(batchItem.getEndWeak());
            orderProductInfoVo.setDuration(batchItem.getDuration());
            Integer res = Integer.valueOf(batchItem.getTotalNum()) - batchItem.getSaleNum();
            orderProductInfoVo.setSurplusNum(res);
        }

        return orderProductInfoVo;
    }

    /**
     * 处理前端需要的照片信息
     *
     * @param product
     */
    private static void parserImage(Product product) {
        String sliderUrls = product.getSliderUrls();
        System.out.println("sliderUrls.contains(\",\") = " + sliderUrls.contains(","));
        if (sliderUrls.contains(",")) {
            String[] urls = sliderUrls.split(",");
            product.setSliderImageUrls(Arrays.stream(urls).toList());
            product.setSliderUrls(urls[0]);
        } else {
            product.setSliderImageUrls(Stream.of(sliderUrls).toList());
            product.setSliderUrls(sliderUrls);
        }
    }
}

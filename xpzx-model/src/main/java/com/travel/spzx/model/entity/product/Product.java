package com.travel.spzx.model.entity.product;

import com.travel.spzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "商品实体类")
public class Product extends BaseEntity {

    @Schema(description = "商品名称")
    private String name;                    // 商品名称

    @Schema(description = "品牌id")
    private Long brandId;                    // 品牌ID

    @Schema(description = "一级分类id")
    private Long category1Id;                // 一级分类id

    @Schema(description = "二级分类id")
    private Long category2Id;                // 二级分类id

    @Schema(description = "三级分类id")
    private Long category3Id;                // 三级分类id

    @Schema(description = "计量单位")
    private String unitName;                // 计量单位

    @Schema(description = "轮播图url")
    private String sliderUrls;                // 轮播图
    @Schema(description = "详情图url,用于返回给前端")
    private List<String> sliderImageUrls;                         // 详情图

    @Schema(description = "商品规格值json串")
    private String specValue;                // 商品规格值json串

    @Schema(description = "线上状态：0-初始值，1-上架，-1-自主下架")
    private Integer status;                    // 线上状态：0-初始值，1-上架，-1-自主下架

    @Schema(description = "审核状态")
    private Integer auditStatus;            // 审核状态

    @Schema(description = "审核信息")
    private String auditMessage;            // 审核信息

    // 扩展的属性，用来封装响应的数据
    @Schema(description = "品牌名称")
    private String brandName;                // 品牌

    @Schema(description = "一级分类名称")
    private String category1Name;            // 一级分类

    @Schema(description = "二级分类名称")
    private String category2Name;            // 二级分类

    @Schema(description = "三级分类名称")
    private String category3Name;            // 三级分类

    @Schema(description = "sku列表集合")
    private List<ProductSku> productSkuList;        // sku列表集合
    @Schema(description = "批次列表集合")
    private List<BatchItem> batchInfo;        // sku列表集合

    @Schema(description = "图片详情列表")
    private String detailsImageUrls;                // 图片详情列表
    @Schema(description = "推荐等级,用于banner和排序")
    private String recommendLevel;                // 图片详情列表

    @Schema(description = "出发地点id")
    private Long placeToStartId;            // 推荐起点id

    @Schema(description = "出发地点名字")
    private String startAddress;            // 推荐起点id
    @Schema(description = "出发地点名字")
    private String startCityName;            // 推荐起点id

    @Schema(description = "特点")
    private String feature;                // 特点
    @Schema(description = "简介信息")
    private String description;                // 特点


    /**
     * 同类推荐的其他商品
     */
    @Schema(description = "同类推荐的其他商品")
    private List<Product> similarProducts;                    // 价格
    @Schema(description = "集合时间")
    private String assembleTime;




}
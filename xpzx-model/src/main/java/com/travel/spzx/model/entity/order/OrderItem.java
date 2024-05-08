package com.travel.spzx.model.entity.order;

import com.travel.spzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "订单项实体类")
public class OrderItem extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @Schema(description = "订单id")
    private Long orderId;

    @Schema(description = "商品sku编号")
    private Long skuId;

    @Schema(description = "商品sku名字")
    private String skuName;

    @Schema(description = "批次id")
    private Long batchId;

    @Schema(description = "批次id")
    private Long productId;

    @Schema(description = "批次名字")
    private String batchName;
    @Schema(description = "商品sku图片")
    private String thumbImg;

    @Schema(description = "成人价格")
    private BigDecimal adultPrice;
    @Schema(description = "儿童价格")
    private BigDecimal childPrice;
    @Schema(description = "出行人id")
    private Long tripId;

}
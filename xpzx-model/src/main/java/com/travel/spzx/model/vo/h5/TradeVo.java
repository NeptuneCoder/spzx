package com.travel.spzx.model.vo.h5;

import com.travel.spzx.model.entity.order.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "结算实体类")
public class TradeVo {

    @Schema(description = "结算总金额")
    private BigDecimal totalAmount;

    @Schema(description = "结算商品列表")
    private List<OrderItem> orderItemList;

}
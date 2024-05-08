package com.travel.spzx.model.vo.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderProductInfoVo {
    private String productId;
    private String name;
    private String feature;
    private String startDate;
    private String endDate;
    private String startWeak;
    private String endWeak;
    private String duration;
    private BigDecimal adultPrice;
    private BigDecimal childPrice;
    @Schema(description = "剩余数量")
    private int surplusNum;


}

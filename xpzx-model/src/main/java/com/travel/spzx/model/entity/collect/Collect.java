package com.travel.spzx.model.entity.collect;

import com.travel.spzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "Collect", description = "收藏")
public class Collect extends BaseEntity {

    @Schema(description = "商品id")
    private Long productId;

    @Schema(description = "商品名称")
    private String productName;

    @Schema(description = "商品图片")
    private String productImg;

    @Schema(description = "商品价格")
    private String adultPrice;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "收藏状态，0：未收藏，1：已收藏")
    private Integer status;
}

package com.travel.spzx.model.entity.order;

import com.travel.spzx.model.entity.base.BaseEntity;
import com.travel.spzx.model.status.OrderStateEnum;
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


    @Schema(description = "批次id")
    private Long batchId;
    @Schema(description = "金额")
    private BigDecimal orderAmount;

    @Schema(description = "订单状态")
    private Integer orderStatus;
    @Schema(description = "签到状态")
    private String checkStatus;

    @Schema(description = "订单状态描述")
    private String orderStatusText;
    @Schema(description = "批次id")
    private Long productId;

    @Schema(description = "出行人id")
    private Long tripId;
    @Schema(description = "出行人姓名")
    private String tripName;
    @Schema(description = "出行人身份证号")
    private String tripCardNo;

    private String sex;

    private String ageType;


    public void convertStatusText() {
        this.orderStatusText = OrderStateEnum.getMessage(this.orderStatus);
    }

    @Schema(description = "分配的车牌号")
    private String carNo;
    @Schema(description = "导游昵称")
    private String guideNickname;
    @Schema(description = "导游手机号")
    private String guidePhone;

}
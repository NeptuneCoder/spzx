package com.travel.spzx.model.entity.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.spzx.model.entity.base.BaseEntity;
import com.travel.spzx.model.entity.trip.TripInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Schema(description = "OrderInfo")
public class OrderInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;
    @Schema(description = "会员_id")
    private Long userId;
    @Schema(description = "昵称")
    private String nickName;
    @Schema(description = "商品id")
    private Long productId;
    @Schema(description = "商品名称")
    private String productName;
    private String sliderUrls;
    private String feature;
    @Schema(description = "集合时间")
    private String assembleTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Schema(description = "出发时间")
    private Date assembleDate;
    private String assembleWeek;
    private String endTime;

    private String address;
    private String cityName;


    @Schema(description = "skuId")
    private Long skuId;
    @Schema(description = "批次id")
    private Long batchId;
    @Schema(description = "过期时间")
    private Long countdown = 180 * 10L;


    @Schema(description = "联系人姓名")
    private String linkMan;
    @Schema(description = "联系人电话")
    private String linkTel;

    @Schema(description = "adultPrice")
    private BigDecimal adultPrice;
    @Schema(description = "childPrice")
    private BigDecimal childPrice;
    @Schema(description = "adultNum")
    private Integer adultNum;
    @Schema(description = "childNum")
    private Integer childNum;
    @Schema(description = "订单号")
    private String orderNo;

    @Schema(description = "使用的优惠券")
    private Long couponId;

    @Schema(description = "订单总额")
    private BigDecimal totalAmount;

    @Schema(description = "优惠券")
    private BigDecimal couponAmount;

    @Schema(description = "原价金额")
    private BigDecimal originalTotalAmount;

    private Long insurance;
    private String insuranceNo;
    @Schema(description = "支付方式【1->微信】 2->支付宝 3->银联 4->其他")
    private Integer payType;

    @Schema(description = "订单状态【0->待付款；1->已付款；2->未核验；3->已完成；-1->取消,-2->退款, -3->支付超时】")
    private Integer orderStatus;

    //评价详情id
    private Long evaluateId;
    @Schema(description = "支付时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date paymentTime;
    //消费时间
    @Schema(description = "消费时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date consumeTime;
    @Schema(description = "订单备注")
    private String remark;

    @Schema(description = "取消订单时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cancelTime;

    @Schema(description = "取消订单原因")
    private String cancelReason;

    @Schema(description = "订单项列表")
    private List<OrderItem> orderItemList;

    private String category2Id;

}
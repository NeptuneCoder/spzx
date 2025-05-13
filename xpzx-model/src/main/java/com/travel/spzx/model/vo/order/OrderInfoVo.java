package com.travel.spzx.model.vo.order;

import com.alibaba.excel.event.Order;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.travel.spzx.model.entity.base.BaseEntity;
import com.travel.spzx.model.entity.order.OrderInfo;
import com.travel.spzx.model.entity.order.OrderItem;
import com.travel.spzx.model.status.BatchStatusEnum;
import com.travel.spzx.model.status.OrderStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Schema(description = "OrderInfo")
public class OrderInfoVo extends OrderInfo {

    private String productName;
    /**
     * p.name as product_name,
     * bi.start_time as start_time,
     * bi.end_time as end_time,
     * ps.sku_name as sku_name
     */

    private String startTime;
    private String endTime;
    private String skuName;
    private String orderStatusText;

    public void convertStatusText() {
        this.orderStatusText = OrderStateEnum.getMessage(this.getOrderStatus());
    }

    public void convertBatchStatusText() {
        this.batchStatusText = BatchStatusEnum.getEnumDesc(this.getBatchStatus());
    }

    private int batchStatus;
    private String batchStatusText;

}
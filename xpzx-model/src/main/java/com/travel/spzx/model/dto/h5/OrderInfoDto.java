package com.travel.spzx.model.dto.h5;

import com.travel.spzx.model.dto.trip.TripInfoDto;
import com.travel.spzx.model.entity.order.OrderItem;
import com.travel.spzx.model.entity.trip.TripInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderInfoDto {
    /**
     * productId: string
     * batchId: string
     * linkman: string
     * phone: string
     * trips: string[]
     */
    //送货地址id
    @Schema(description = "产品id")
    private Long productId;
    @Schema(description = "批次id")
    private String batchId;
    @Schema(description = "联系人")
    private String linkman;
    @Schema(description = "联系电话")
    private String phone;
    @Schema(description = "成人数量")
    private Integer adultNum;
    @Schema(description = "儿童数量")
    private Integer childNum;

    @Schema(description = "remarks")
    private String remark;
    @Schema(description = "出行人信息")
    private List<TripInfoDto> trips;
}
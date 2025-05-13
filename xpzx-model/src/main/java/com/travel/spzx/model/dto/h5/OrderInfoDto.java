package com.travel.spzx.model.dto.h5;

import com.travel.spzx.model.dto.trip.TripInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Schema(description = "倒计时")
    private Long countdown;
    @Schema(description = "产品id")
    private Long productId;
    @Schema(description = "批次id")
    private String batchId;
    @NotBlank
    @Schema(description = "联系人")
    private String linkMan;
    @Pattern(regexp = "^1[34578]\\d{9}$", message = "手机号码格式不正确")
    @Schema(description = "联系电话")
    private String linkTel;

    //1或者大于1的整数
    @Pattern(regexp = "^[1-9]\\d*$", message = "成人数量格式不正确")
    @Schema(description = "成人数量")
    private Integer adultNum;
    @Schema(description = "儿童数量")
    private Integer childNum;

    @Schema(description = "remarks")
    private String remark;
    @NotBlank
    @Schema(description = "出行人信息")
    private List<TripInfoDto> trips;


}
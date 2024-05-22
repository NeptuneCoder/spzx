package com.travel.spzx.model.vo.batch;

import com.travel.spzx.model.status.OrderStateEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
@Schema(description = "领队端批次详情")
public class TouristDetailVo {
    private String tripId;
    private String orderId;
    private String orderItemId;

    private String name;
    private String sex;
    private String ageType;
    private String orderStatus;
    private String checkStatus;


    public String getStatusStr1() {
        return checkStatus.equals("1") ? "已签到" : "未签到";
    }

    private String statusStr;
    @Schema(description = "手机号码")
    private String phone;
    @Schema(description = "证件号码")
    private String cardNo;
}

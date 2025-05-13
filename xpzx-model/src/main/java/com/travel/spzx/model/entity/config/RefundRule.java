package com.travel.spzx.model.entity.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RefundRule {
    @Schema(description = "当日退款")
    private Integer refund0Days;
    @Schema(description = "提前1到2天退款")
    private Integer refund12Days;
    @Schema(description = "提前3天退款")
    private Integer refund3Days;

}

package com.travel.spzx.model.dto.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CancelReasonDto {
    @Schema(description = "取消原因")
    private String cancelReason;
}

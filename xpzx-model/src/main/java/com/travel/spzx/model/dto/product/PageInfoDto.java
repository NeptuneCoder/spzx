package com.travel.spzx.model.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "分页信息")
public class PageInfoDto {
    @Schema(description = "页码")
    private int page;
    @Schema(description = "每页数量")
    private int limit;
}

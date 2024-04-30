package com.travel.spzx.model.entity.assemble;

import com.travel.spzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "集合地址信息")
public class AssembleLoc extends BaseEntity {
    @Schema(description = "城市id")
    private String cityId;
    @Schema(description = "城市名称")

    private String cityName;
    @Schema(description = "集合地点名称")
    private String address;
    @Schema(description = "详细地址")
    private String remark;
}

package com.travel.spzx.model.entity.location;

import com.travel.spzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "乘车地址信息")
public class LocationInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Schema(description = "乘车地址")
    private String address;
    @Schema(description = "乘车城市id")
    private String cityId;
    @Schema(description = "备注信息")
    private String remark;


}

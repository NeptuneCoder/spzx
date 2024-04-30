package com.travel.spzx.model.entity.city;

import com.travel.spzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "城市信息")
public class CityInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @Schema(description = "城市名称")
    private String cityName;
    @Schema(description = "备注")
    private String remark;
}

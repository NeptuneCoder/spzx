package com.atguigu.spzx.model.dto.city;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "城市信息")
public class CityInfoDto {

    @Schema(description = "城市名称")
    private String cityName;
    @Schema(description = "备注")
    private String remark;

}

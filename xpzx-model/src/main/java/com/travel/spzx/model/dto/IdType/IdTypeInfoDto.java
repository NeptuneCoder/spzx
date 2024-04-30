package com.travel.spzx.model.dto.IdType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "证件类型信息")
public class IdTypeInfoDto {
    @Schema(description = "证件类型名称")
    private String idTypeName;
    @Schema(description = "证件类型描述")
    private String remark;
}

package com.travel.spzx.model.entity.guide;


import com.travel.spzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "导游等级")
public class TourLevel extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Schema(description = "等级名称")
    private String name;
    @Schema(description = "导游等级")
    private String level;
    @Schema(description = "备注信息")
    private String remark;
}

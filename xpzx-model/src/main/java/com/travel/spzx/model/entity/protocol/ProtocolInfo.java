package com.travel.spzx.model.entity.protocol;

import com.travel.spzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "品牌实体类")
public class ProtocolInfo extends BaseEntity {
    @Schema(description = "标题")
    private String title;

    @Schema(description = "key")
    private String protocolKey;
    @Schema(description = "baseUrl")
    private String baseUrl;
    @Schema(description = "内容")
    private String content;
    @Schema(description = "备注")
    private String remark;
}

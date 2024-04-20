package com.atguigu.spzx.model.entity.richTxt;

import com.atguigu.spzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "品牌实体类")
public class RichTxtInfo extends BaseEntity {
    @Schema(description = "标题")
    private String title;
    @Schema(description = "内容")
    private String content;
    @Schema(description = "类型")
    private int type;
    @Schema(description = "备注")
    private String remark;
}

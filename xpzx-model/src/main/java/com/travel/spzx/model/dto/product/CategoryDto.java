package com.travel.spzx.model.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "分类信息")
public class CategoryDto {
    //    /** 图标路径 */
//    icon: string
//    /** id */
//    id: string
//    /** 分类名称 */
//    name: string
    @Schema(description = "图标路径")
    private String icon;
    @Schema(description = "分类名称")
    private String name;
    @Schema(description = "分类id")
    private String categoryId;
    @Schema(description = "子分类")
    private List<CategoryDto> children;
}

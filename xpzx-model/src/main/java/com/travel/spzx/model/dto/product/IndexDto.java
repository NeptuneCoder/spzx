package com.travel.spzx.model.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "首页的基本信息：banner，category")
public class IndexDto {
    @Schema(description = "首页banner")
    List<BannerDto> bannerList;
    @Schema(description = "首页一级分类")
    List<CategoryDto> oneCategoryList;

}

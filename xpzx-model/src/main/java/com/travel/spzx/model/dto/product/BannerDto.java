package com.travel.spzx.model.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "banner信息")
public class BannerDto {
    //    /** 跳转链接 */
//    hrefUrl: string
//    /** id */
//    id: string
//    /** 图片链接 */
//    imgUrl: string
//    /** 跳转类型 */
//    type: number
    @Schema(description = "商品id")
    private String id;
    @Schema(description = "类型icon")
    private String imgUrl;
}

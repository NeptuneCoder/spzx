package com.atguigu.spzx.model.entity.guide;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.atguigu.spzx.model.entity.base.BaseEntity;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Schema(description = "导游信息")
public class TourGuideInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @Schema(description = "导游姓名")
    private String name;
    @Schema(description = "导游昵称")
    private String nickname;
    @Schema(description = "导游电话号码")
    private String phone;
    @Schema(description = "证件类型")
    private String certificateType;
    @Schema(description = "证件类型名字")
    private String certificateTypeName;
    @Schema(description = "身份证号码")
    private String identityCard;
    @Schema(description = "导游星级，自己定义的导游等级")
    private String level;
    @Schema(description = "导游证书[0 没有 1 有]")
    private Long tourCertificate;
    @Schema(description = "导游编号")
    private String tourCertificateNo;
    @Schema(description = "带队次数")
    private Long leadTimes;
    @Schema(description = "评分")
    private BigDecimal score;
    @Schema(description = "备注信息")
    private String remark;
}

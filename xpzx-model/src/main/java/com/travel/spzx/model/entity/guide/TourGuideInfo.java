package com.travel.spzx.model.entity.guide;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import com.travel.spzx.model.entity.base.BaseEntity;

import java.math.BigDecimal;

@Data
@Schema(description = "导游信息")
public class TourGuideInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @NotBlank
    @Size(min = 2, max = 20, message = "导游姓名长度必须在2-20之间")
    @Schema(description = "导游姓名")
    private String name;
    @Size(min = 2, max = 20, message = "导游姓名长度必须在2-20之间")
    @Schema(description = "导游昵称")
    private String nickname;
    @Pattern(regexp = "^1[34578]\\d{9}$", message = "手机号码格式不正确")
    @Schema(description = "导游电话号码")
    private String phone;
    @NotBlank
    @Schema(description = "证件类型")
    private String idTypeId;
    @Schema(description = "证件类型名字")
    private String idTypeName;
    @Pattern(regexp = "^\\d{18}$|^\\d{15}$", message = "身份证号码格式不正确")
    @Schema(description = "身份证号码")
    private String idTypeNo = "";
    @Schema(description = "导游等级名称")
    private String levelName;
    @Schema(description = "导游星级，自己定义的导游等级")
    private String level;
    @Schema(description = "导游证书[0 没有 1 有]")
    private Long tourCertificate;
    @Schema(description = "导游编号")
    private String tourCertificateNo = "";
    @Schema(description = "带队次数")
    private Long leadTimes = 0L;
    @Schema(description = "评分")
    private BigDecimal score = new BigDecimal(0);
    @Schema(description = "备注信息")
    private String remark = "";
}

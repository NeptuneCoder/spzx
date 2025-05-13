package com.travel.spzx.model.dto.trip;

import com.travel.spzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "出行人信息管理")
public class TripInfoDto {
    //    name: string
//    idType: number
//    idNo: string
//    sex: Gender
//
//    mobile: string
//
//    birthday: string
//
//    isDefault: number
    @Schema(description = "主键id")
    private Long id;
    @Schema(description = "订单id,create order 时生成")
    private Long orderId;
    @Schema(description = "用户id")
    private Long userId;
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "证件类型")
    private int idCardType;
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$", message = "身份证号码格式不正确")
    @Schema(description = "证件号码")
    private String idCardNo;
    @Schema(description = "性别")
    private String sex;

    @Pattern(regexp = "^1[34578]\\d{9}$", message = "手机号格式不正确")
    @Schema(description = "手机号")
    private String phone;
    @Pattern(regexp = "^\\d{4}(\\-|\\/|\\.)\\d{1,2}(\\-|\\/|\\.)\\d{1,4}$", message = "日期格式不正确")
    @Schema(description = "出生日期")
    private String birthday;

    @Schema(description = "是否默认出行人")
    private int defaultTrip;
    @Pattern(regexp = "child|adult", message = "身份证号码格式不正确")
    @Schema(description = "年龄类型:儿童，成人")
    private String ageType;


}

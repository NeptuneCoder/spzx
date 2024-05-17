package com.travel.spzx.model.entity.trip;

import com.travel.spzx.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(description = "出行人信息管理")
public class TripInfo extends BaseEntity {
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
    @Schema(description = "订单id,create order 时生成")
    private Long orderId;
    @Schema(description = "用户id")
    private Long userId;
    @Schema(description = "姓名")
    private String name;
    //类型只能为0,1,2
    @Pattern(regexp = "^(0|1|2)$", message = "身份证号码格式错误")
    @Schema(description = "证件类型")
    private int idCardType;
    @Schema(description = "证件号码")
    private String idCardNo;
    @Pattern(regexp = "^(男|女)$", message = "性别格式错误")
    @Schema(description = "性别")
    private String sex;
    @Pattern(regexp = "^1\\d{10}$", message = "手机号格式错误")
    @Schema(description = "手机号")
    private String phone;
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "日期格式错误")
    @Schema(description = "出生日期")
    private String birthday;

    @Schema(description = "是否默认出行人")
    private int defaultTrip;
    @Pattern(regexp = "^(儿童|成人)$", message = "年龄类型格式错误")
    @Schema(description = "年龄类型:儿童，成人")
    private String ageType;


}

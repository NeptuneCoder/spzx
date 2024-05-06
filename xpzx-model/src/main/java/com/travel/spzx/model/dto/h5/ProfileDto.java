package com.travel.spzx.model.dto.h5;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户信息更新")
public class ProfileDto {
    //'nickName' | 'name' | 'birthday' | 'idType' | 'idCardNo'
    @Schema(description = "昵称")
    private String nickName;
    @Schema(description = "姓名")
    private String name;
    @Schema(description = "出生日期")
    private String birthday;
    // 0:身份证 1:护照 2:其他
    @Schema(description = "证件类型")
    private String idCardType;
    @Schema(description = "证件号码")
    private String idCardNo;


}

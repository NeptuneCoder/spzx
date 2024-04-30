package com.travel.spzx.model.dto.h5;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "用户登录请求参数")
public class UserLoginCodeDto {

    @Schema(description = "用户名")
    private String phone;

    @Schema(description = "密码")
    private String code;
}
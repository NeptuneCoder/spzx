package com.atguigu.spzx.model.vo.h5;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
//
///** 通用的用户信息 */
//type BaseProfile = {
//        /** 用户ID */
//        id: number
//        /** 头像  */
//        avatar: string
//        /** 账户名  */
//        account: string
//        /** 昵称 */
//        nickname?: string
//        }
//
///** 小程序登录 登录用户信息 */
//        export type LoginResult = BaseProfile & {
//        /** 手机号 */
//        mobile: string
//        /** 登录凭证 */
//        token: string
//        }

@Data
@Schema(description = "用户类")
public class UserInfoVo {
    @Schema(description = "头像")
    private String avatar;
    @Schema(description = "账户名")
    private String username;

    @Schema(description = "昵称")
    private String nickName;
    @Schema(description = "手机号")
    private String phone;
    @Schema(description = "登录凭证")
    private String token;


}
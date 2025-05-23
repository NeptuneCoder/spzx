package com.travel.spzx.model.vo.common;

import lombok.Getter;

@Getter // 提供获取属性值的getter方法
public enum ResultCodeEnum {

    SUCCESS(200, "操作成功"), //
    LOGIN_ERROR(201, "用户名或者密码错误"), //
    VALIDATECODE_ERROR(202, "验证码错误"), //
    LOGIN_AUTH(208, "用户未登录"), //
    USER_NAME_IS_EXISTS(209, "用户名已经存在"), //
    SYSTEM_ERROR(9999, "您的网络有问题请稍后重试"), //
    NODE_ERROR(217, "该节点下有子节点，不可以删除"), //
    DATA_ERROR(204, "数据异常"), //
    ACCOUNT_STOP(216, "账号已停用"),//

    STOCK_LESS(219, "库存不足"),//
    UPLOAD_ERROR(220, "文件上传失败"), //
    FILE_SERVICE_IP_ERROR(221, "文件服务IP地址错误"),//
    FILE_SERVICE_ERROR(223, ""), //
    PHONE_FORMAT_ERROR(224, "手机号格式错误"),//
    USER_NOT_EXISTS(225, "用户名不存在"), //
    STOCK_NUM_IS_ERROR(226, "库存数量错误"), //
    DELETE_ADMIN_USER_ERROR(227, "系统Admin用户不能删除"),//
    DELETE_SELF_USER_ERROR(228, "不能删除自己"),//

    ADDRESS_DUPLICATION_ERROR(229, "城市不能重复"),//
    TOUR_LEVEL_EXISTS_ERROR(300, "有领队使用该等级，不能修改"),//
    LEVEL_DUPLICATION_ERROR(301, "等级不能重复"),//
    PARAM_ERROR(302, "参数错误"),//
    ;

    private Integer code;      // 业务状态码
    private String message;    // 响应消息

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}

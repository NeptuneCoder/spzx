package com.atguigu.spzx.common.exception;

import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

@Data
public class GuiguException extends RuntimeException {
    private Integer code;          // 错误状态码
    private String message;        // 错误消息

    private ResultCodeEnum resultCodeEnum;     // 封装错误状态码和错误消息

    public GuiguException(ResultCodeEnum resultCodeEnum) {
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    public static GuiguException build(String message) {
        return new GuiguException(message);
    }

    public GuiguException(String message) {
        this.code = ResultCodeEnum.PARAM_ERROR.getCode();
        this.message = message;
        this.resultCodeEnum = ResultCodeEnum.PARAM_ERROR;
    }

    public GuiguException(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.resultCodeEnum = ResultCodeEnum.PARAM_ERROR;
    }

    public GuiguException(ResultCodeEnum code, String message) {
        this.code = code.getCode();
        this.message = message;
    }


}

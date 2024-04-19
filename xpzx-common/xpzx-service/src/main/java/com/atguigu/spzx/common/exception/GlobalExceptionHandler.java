package com.atguigu.spzx.common.exception;

import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalExceptionHandler {
    //指定发生Exception类型的异常时执行该方法，全局的异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(Exception e) {
        e.printStackTrace();
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR, e.getMessage());
    }

    //指定发生GuiguException类型的异常时执行该方法，自定义的异常处理
    @ExceptionHandler(GuiguException.class)
    @ResponseBody
    public Result handleException(GuiguException e) {
        return Result.build(null, e.getResultCodeEnum());
    }
}

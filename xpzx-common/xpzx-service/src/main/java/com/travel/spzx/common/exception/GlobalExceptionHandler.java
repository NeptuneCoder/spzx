package com.travel.spzx.common.exception;

import com.alibaba.fastjson.JSON;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler {
    //指定发生Exception类型的异常时执行该方法，全局的异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(Exception e) {
        e.printStackTrace();
        System.out.println(e.getClass().getName());
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR, e.getMessage());
    }


    //处理ValidationException异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handleException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return Result.build(null, ResultCodeEnum.PARAM_ERROR, JSON.toJSONString(errors));
    }

    //指定发生GuiguException类型的异常时执行该方法，自定义的异常处理
    @ExceptionHandler(GuiguException.class)
    @ResponseBody
    public Result handleException(GuiguException e) {
        System.out.println("error message:" + e.getMessage());
        return Result.build(null, e.getResultCodeEnum());
    }
}

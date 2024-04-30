package com.travel.spzx.common.log.utils;

import com.travel.spzx.common.log.annotation.Log;
import com.travel.spzx.model.entity.system.SysOperLog;
import com.travel.spzx.model.entity.system.SysUser;
import com.travel.xpzx.utils.AuthContextUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;

import java.lang.reflect.Method;
import java.util.Arrays;


public class LogUtil {
    //操作执行之后调用
    public static void afterHandleLog(Log sysLog, Object proceed, SysOperLog sysOperLog, int status, String errorMsg) {
        if (sysLog.isSaveResponseData()) {
            try {
                String s = proceed.toString();
                System.out.println("proceed:" + s);
//                sysOperLog.setJsonResult(JSON.toJSONString(proceed));
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        sysOperLog.setStatus(status);
        sysOperLog.setErrorMsg(errorMsg);
    }

    //操作执行之前调用
    public static void beforeHandleLog(Log sysLog, ProceedingJoinPoint joinPoint, SysOperLog sysOperLog) {

        // 设置操作模块名称
        sysOperLog.setTitle(sysLog.title());
        sysOperLog.setOperatorType(sysLog.operatorType().name());

        // 获取目标方法信息
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        sysOperLog.setMethod(method.getDeclaringClass().getName());

        // 获取请求相关参数
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        sysOperLog.setRequestMethod(request.getMethod());
        sysOperLog.setOperUrl(request.getRequestURI());
        sysOperLog.setOperIp(request.getRemoteAddr());

        // 设置请求参数
        if (sysLog.isSaveRequestData()) {
            String requestMethod = sysOperLog.getRequestMethod();
            if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
                String params = Arrays.toString(joinPoint.getArgs());
                sysOperLog.setOperParam(params);
            }
        }
        SysUser sysUser = AuthContextUtil.get();
        if (sysUser != null) {
            sysOperLog.setOperName(sysUser.getUserName());
        } else {
            sysOperLog.setOperName("匿名用户:" + sysLog.title());
        }

    }
}

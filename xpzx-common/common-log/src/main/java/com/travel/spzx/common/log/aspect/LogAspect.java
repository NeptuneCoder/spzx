package com.travel.spzx.common.log.aspect;


import com.travel.spzx.common.log.annotation.Log;
import com.travel.spzx.common.log.service.AsyncOperLogService;
import com.travel.spzx.common.log.utils.LogUtil;
import com.travel.spzx.model.entity.system.SysOperLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Aspect
@Component
public class LogAspect {
    @Autowired
    AsyncOperLogService asyncOperLogService;

    //环绕通知
    @Around(value = "@annotation(sysLog)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log sysLog) {
        SysOperLog sysOperLog = new SysOperLog();
        LogUtil.beforeHandleLog(sysLog, joinPoint, sysOperLog);
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();              // 执行业务方法
            LogUtil.afterHandleLog(sysLog, joinPoint, sysOperLog, 0, "");
        } catch (Throwable e) {                         // 代码执行进入到catch中，业务方法执行产生异常
            e.printStackTrace();
            LogUtil.afterHandleLog(sysLog, joinPoint, sysOperLog, 1, e.getMessage());
            //改行代码解决事务失效的问题；由于系统的事务也是通过切面实现的，如果次数抛出了异常，会导致事务切面的感知不到异常，致使事务在发生异常时不会进行回滚。
            throw new RuntimeException(e);

        }
        asyncOperLogService.saveSysOperLog(sysOperLog);
        return proceed;                                // 返回执行结果
    }

}

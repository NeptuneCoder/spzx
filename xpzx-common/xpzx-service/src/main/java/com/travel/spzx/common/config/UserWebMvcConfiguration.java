package com.travel.spzx.common.config;

import com.travel.spzx.common.interceptor.UserLoginAuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 该拦截器的作用是：
 * 1. 登录拦截器：检查用户是否登录，如果未登录，则跳转到登录页面
 * 2. 权限拦截器：检查用户是否有访问该页面的权限，如果没有权限，则跳转到无权限页面
 * 3. 日志拦截器：记录用户的操作日志
 */
@Component
public class UserWebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private UserLoginAuthInterceptor userLoginAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginAuthInterceptor)
                .addPathPatterns("/api/**");
    }
}
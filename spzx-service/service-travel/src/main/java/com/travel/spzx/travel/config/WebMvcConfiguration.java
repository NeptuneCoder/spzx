package com.travel.spzx.travel.config;

import com.travel.spzx.travel.interceptor.LoginAuthInterceptor;
import com.travel.spzx.travel.properties.UserAuthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    private LoginAuthInterceptor loginInterceptor;

    @Autowired
    private UserAuthProperties userAuthProperties;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")      // 添加路径规则
                .allowCredentials(true)               // 是否允许在跨域的情况下传递Cookie
                .allowedOriginPatterns("*")           // 允许请求来源的域规则
                .allowedMethods("*")
                .allowedHeaders("*");                // 允许所有的请求头
    }

    //添加拦截器，表示那些请求需要拦截，哪些请求不需要拦截
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //打印l不拦截的地址：
        userAuthProperties.getNoAuthUrls().forEach(System.out::println);
        registry.addInterceptor(loginInterceptor)
                .excludePathPatterns(userAuthProperties.getNoAuthUrls())// 不拦截登录请求
                .addPathPatterns("/**");// 拦截所有请求
    }
}
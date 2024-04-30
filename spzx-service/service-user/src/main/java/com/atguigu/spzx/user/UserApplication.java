package com.atguigu.spzx.user;

import com.atguigu.spzx.common.anno.EnableUserLoginAuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu.spzx"})
@EnableUserLoginAuthInterceptor//利用该注解可以将用户信息写入到ThreadLocal中，供后续的拦截器使用
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);

    }
}

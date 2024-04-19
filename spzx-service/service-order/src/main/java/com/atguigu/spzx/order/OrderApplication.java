package com.atguigu.spzx.order;

import com.atguigu.spzx.common.anno.EnableUserLoginAuthInterceptor;
import com.atguigu.spzx.common.anno.EnableUserTokenFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableUserLoginAuthInterceptor
//开启支持远程调用
@EnableFeignClients(basePackages = {
        "com.atguigu.spzx.feign.cart",
        "com.atguigu.spzx.feign.product",
        "com.atguigu.spzx.feign.user"
})
@EnableUserTokenFeignInterceptor
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}

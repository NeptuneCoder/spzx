package com.travel.spzx.order;

import com.travel.spzx.common.anno.EnableUserLoginAuthInterceptor;
import com.travel.spzx.common.anno.EnableUserTokenFeignInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableUserLoginAuthInterceptor
//开启支持远程调用
@EnableFeignClients(basePackages = {
        "com.travel.spzx.feign.cart",
        "com.travel.spzx.feign.product",
        "com.travel.spzx.feign.user"
})
@EnableUserTokenFeignInterceptor
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}

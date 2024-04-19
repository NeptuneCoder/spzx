package com.atguigu.spzx.cart;

import com.atguigu.spzx.common.anno.EnableUserLoginAuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
// 开启Feign客户端, 扫描com.atguigu.spzx.feign.product包下的所有类
@EnableFeignClients(basePackages = {
        "com.atguigu.spzx.feign.product"
})
@EnableUserLoginAuthInterceptor
public class CartApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }
}

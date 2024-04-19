package com.atguigu.spzx.pay;

import com.atguigu.spzx.common.anno.EnableUserLoginAuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import com.atguigu.spzx.pay.properties.AlipayProperties;

@SpringBootApplication
@EnableFeignClients(basePackages = {
        "com.atguigu.spzx.feign.order",
        "com.atguigu.spzx.feign.product",
})
@EnableConfigurationProperties(value = {AlipayProperties.class})
@EnableUserLoginAuthInterceptor
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}

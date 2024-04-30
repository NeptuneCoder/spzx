package com.travel.spzx.pay;

import com.travel.spzx.common.anno.EnableUserLoginAuthInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import com.travel.spzx.pay.properties.AlipayProperties;

@SpringBootApplication
@EnableFeignClients(basePackages = {
        "com.travel.spzx.feign.order",
        "com.travel.spzx.feign.product",
})
@EnableConfigurationProperties(value = {AlipayProperties.class})
@EnableUserLoginAuthInterceptor
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}

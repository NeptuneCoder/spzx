package com.travel.spzx.travel;

import com.travel.spzx.common.anno.EnableUserLoginAuthInterceptor;
import com.travel.spzx.common.anno.EnableUserTokenFeignInterceptor;
import com.travel.spzx.travel.properties.FileServiceProperties;
import com.travel.spzx.travel.properties.UserAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@EnableUserLoginAuthInterceptor
@EnableUserTokenFeignInterceptor
@EnableCaching
@ComponentScan(basePackages = {"com.travel.spzx"})
@SpringBootApplication
@EnableConfigurationProperties(value = {UserAuthProperties.class, FileServiceProperties.class})
public class TravelApplication {
    public static void main(String[] args) {
        SpringApplication.run(TravelApplication.class, args);
        // Spring Boot 启动入口
        // 启动类上添加 @SpringBootApplication 注解，Spring Boot 自动配置生效，项目启动成功
        // 启动类上添加 @EnableDiscoveryClient 注解，服务发现功能生效，可以注册到注册中心
        // 启动类上添加 @EnableFeignClients 注解，Feign 客户端功能生效，可以调用远程服务
    }
}

package com.atguigu.spzx.manager;

import com.atguigu.spzx.common.log.annotation.EnableLogAspect;
import com.atguigu.spzx.manager.properties.FileServiceProperties;
import com.atguigu.spzx.manager.properties.UserAuthProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableLogAspect
@SpringBootApplication
@EnableScheduling
//@ComponentScan 该注解的作用：
@ComponentScan(basePackages = {"com.atguigu.spzx"})//配置扫描路径，解决swagger不生效的问题
@MapperScan("com.atguigu.spzx.manager.mapper")//配置mybatis扫描路径
@EnableConfigurationProperties(value = {UserAuthProperties.class, FileServiceProperties.class})
public class XpzxManagerApp {
    public static void main(String[] args) {
        SpringApplication.run(XpzxManagerApp.class, args);
    }
}

package com.atguigu.spzx.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.atguigu.spzx"})//配置扫描路径，解决swagger不生效的问题
public class XpzxManagerApp {
    public static void main(String[] args) {
        SpringApplication.run(XpzxManagerApp.class, args);
    }
}

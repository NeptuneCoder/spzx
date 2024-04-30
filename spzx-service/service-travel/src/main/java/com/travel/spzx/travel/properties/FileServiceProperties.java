package com.travel.spzx.travel.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

//需要再application中配置
//@EnableConfigurationProperties(value = {UserAuthProperties.class, FileServiceProperties.class})
@Data
@ConfigurationProperties(prefix = "spzx.config")      // 前缀不能使用驼峰命名
public class FileServiceProperties {
    private String url;
    private String bucketName;
    private String account;
    private String password;
}

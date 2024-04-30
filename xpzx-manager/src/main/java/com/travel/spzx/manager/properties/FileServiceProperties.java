package com.travel.spzx.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "spzx.config")      // 前缀不能使用驼峰命名
public class FileServiceProperties {
    private List<String> fileServiceInfo;
}

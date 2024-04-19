package com.atguigu.spzx.pay.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spzx.alipay")      // 前缀不能使用驼峰命名
public class AlipayProperties {

    private String alipayUrl;
    private String appPrivateKey;
    public String alipayPublicKey;
    private String appId;
    public String returnPaymentUrl;
    public String notifyPaymentUrl;

    public final static String format = "json";
    public final static String charset = "utf-8";
    public final static String sign_type = "RSA2";
}

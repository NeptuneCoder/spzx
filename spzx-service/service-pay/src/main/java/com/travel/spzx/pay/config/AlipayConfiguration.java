package com.travel.spzx.pay.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.travel.spzx.pay.properties.AlipayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration注解什么意思？
//作用：将当前类作为Spring Bean实例化，并交由Spring容器管理。
//
@Configuration
public class AlipayConfiguration {
    @Autowired
    private AlipayProperties alipayProperties;

    @Bean
    public AlipayClient alipayClient() {
        AlipayClient alipayClient = new DefaultAlipayClient(alipayProperties.getAlipayUrl(),
                alipayProperties.getAppId(),
                alipayProperties.getAppPrivateKey(),
                AlipayProperties.format,
                AlipayProperties.charset,
                alipayProperties.getAlipayPublicKey(),
                AlipayProperties.sign_type);
        return alipayClient;
    }
}

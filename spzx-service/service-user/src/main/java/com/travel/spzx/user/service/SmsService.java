package com.travel.spzx.user.service;

public interface SmsService {
    void sendValidateCode(String phone, String type);
}

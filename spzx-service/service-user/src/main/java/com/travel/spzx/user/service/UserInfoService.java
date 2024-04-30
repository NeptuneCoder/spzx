package com.travel.spzx.user.service;

import com.travel.spzx.model.dto.h5.UserLoginDto;
import com.travel.spzx.model.dto.h5.UserRegisterDto;
import com.travel.spzx.model.vo.h5.UserInfoVo;

public interface UserInfoService {
    void register(UserRegisterDto userRegisterDto);

    String login(UserLoginDto userLoginDto);

    UserInfoVo getCurrentUserInfo(String token);
}

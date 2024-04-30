package com.atguigu.spzx.travel.service;

import com.atguigu.spzx.model.dto.h5.UserLoginCodeDto;
import com.atguigu.spzx.model.dto.h5.UserLoginDto;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.vo.h5.UserInfoVo;

public interface UserInfoService {
    void register(UserRegisterDto userRegisterDto);

    String login(UserLoginDto userLoginDto);

    UserInfoVo login(UserLoginCodeDto userLogincodeDto);

    UserInfoVo getCurrentUserInfo(String token);

    void closeAccount();

}

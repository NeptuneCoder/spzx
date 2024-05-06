package com.travel.spzx.travel.service;

import com.travel.spzx.model.dto.h5.ProfileDto;
import com.travel.spzx.model.dto.h5.UserLoginCodeDto;
import com.travel.spzx.model.dto.h5.UserLoginDto;
import com.travel.spzx.model.dto.h5.UserRegisterDto;
import com.travel.spzx.model.entity.user.UserInfo;
import com.travel.spzx.model.vo.h5.UserInfoVo;

public interface UserInfoService {
    void register(UserRegisterDto userRegisterDto);

    String login(UserLoginDto userLoginDto);

    UserInfoVo login(UserLoginCodeDto userLogincodeDto);

    UserInfoVo getCurrentUserInfo();

    void updateUserAvatar(String token, UserInfo userInfo);

    void closeAccount();

    void putUserInfo(String token, ProfileDto profileDto);

    void logout(String token);

}

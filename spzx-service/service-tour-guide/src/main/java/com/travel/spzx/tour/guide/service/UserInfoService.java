package com.travel.spzx.tour.guide.service;

import com.travel.spzx.model.dto.h5.ProfileDto;
import com.travel.spzx.model.dto.h5.UserLoginCodeDto;
import com.travel.spzx.model.dto.h5.UserLoginDto;
import com.travel.spzx.model.dto.h5.UserRegisterDto;
import com.travel.spzx.model.entity.tour.TourUserInfo;
import com.travel.spzx.model.entity.user.UserInfo;
import com.travel.spzx.model.vo.h5.UserInfoVo;

public interface UserInfoService {
    void register(UserRegisterDto userRegisterDto);

    String login(UserLoginDto userLoginDto);

    TourUserInfo login(UserLoginCodeDto userLogincodeDto);

    UserInfoVo getCurrentUserInfo();

    void updateUserAvatar(String token, TourUserInfo userInfo);

    void closeAccount();

    void putUserInfo(String token, ProfileDto profileDto);

    void logout(String token);

}

package com.travel.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.entity.user.UserInfo;

public interface UserInfoService {
    PageInfo<UserInfo> list(int page, int limit, String username);

    void update(UserInfo userInfo);

}

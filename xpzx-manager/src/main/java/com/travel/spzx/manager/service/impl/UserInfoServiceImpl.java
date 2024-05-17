package com.travel.spzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travel.spzx.common.exception.GuiguException;
import com.travel.spzx.manager.mapper.UserInfoMapper;
import com.travel.spzx.manager.service.UserInfoService;
import com.travel.spzx.model.entity.user.UserInfo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public PageInfo<UserInfo> list(int page, int limit, String username) {
        PageHelper.startPage(page, limit);
        List<UserInfo> res = userInfoMapper.queryUserListByUsername(username);
        return new PageInfo<>(res);
    }

    @Override
    public void update(UserInfo data) {
        UserInfo userInfo = userInfoMapper.queryById(data.getId());
        if (userInfo != null) {
            if (userInfo.getStatus() == data.getStatus()) {
                throw GuiguException.build("用户更新状态异常");
            }
            userInfoMapper.updateStatus(data.getId(), data.getStatus());
        }
    }
}

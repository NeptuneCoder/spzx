package com.travel.spzx.user.mapper;

import com.travel.spzx.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper {
    UserInfo getByUsername(@Param("username") String username);

    void save(UserInfo userInfo);
}

package com.atguigu.spzx.travel.mapper;

import com.atguigu.spzx.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserInfoMapper {
    UserInfo getByUsername(@Param("username") String username);

    void save(UserInfo userInfo);

    UserInfo getByPhone(@Param("phone") String phone);

    void delete(UserInfo userInfo);
}

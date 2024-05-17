package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.dto.system.SysUserDto;
import com.travel.spzx.model.entity.system.SysUser;
import com.travel.spzx.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//@Mapper注解用于标识这是一个mybatis的mapper接口

/**
 * @author yanghai
 * @version 1.0
 * @date 2021/3/25 16:18
 * @description 该类的作用主要用于查询数据库或者mybatis的mapper接口
 * @email 1454025171@qq.com
 */
@Mapper
public interface UserInfoMapper {


    void forbiddenUser(@Param("userId") Long userId, @Param("status") Integer status);


    List<UserInfo> queryUserListByUsername(String username);

    UserInfo queryById(Long id);

    void updateStatus(@Param("id") Long id, @Param("status") Integer status);
}

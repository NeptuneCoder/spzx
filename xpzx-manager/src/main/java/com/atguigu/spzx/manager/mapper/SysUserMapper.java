package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

//@Mapper注解用于标识这是一个mybatis的mapper接口

/**
 * @author yanghai
 * @version 1.0
 * @date 2021/3/25 16:18
 * @description 该类的作用主要用于查询数据库或者mybatis的mapper接口
 * @email 1454025171@qq.com
 */
@Mapper
public interface SysUserMapper {
    SysUser selectByUsername(String userName);
}

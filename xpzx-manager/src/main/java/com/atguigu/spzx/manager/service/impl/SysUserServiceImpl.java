package com.atguigu.spzx.manager.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.system.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


//@Service 注解表示将service类注册到spring容器中
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    //用于操作redis
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public LoginVo login(LoginDto loginDto) {
        //1. 查询用户是否存在
        SysUser user = sysUserMapper.selectByUsername(loginDto.getUserName());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        //2. 校验密码是否正确
        String dbPassword = user.getPassword();
        //加密后的用户密码
        String inputPassword = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        if (!dbPassword.equals(inputPassword)) {
            throw new RuntimeException("密码错误");
        }

        //3. 生成token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        //4. 将token存入redis,并设置7天过期时间
        redisTemplate.opsForValue().set("user:login" + token, JSON.toJSONString(user), 7, TimeUnit.DAYS);
        //5. 返回登录结果
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }
}

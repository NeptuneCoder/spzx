package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.constant.RedisConstantKey;
import com.atguigu.spzx.common.exception.GuiguException;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.List;
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

        //增加校验验证码逻辑
        String captcha = loginDto.getCaptcha();
        String codeKey = loginDto.getCodeKey();
        String cacheCode = redisTemplate.opsForValue().get(RedisConstantKey.codeKey + codeKey);
        //如果不相等，要删除
        if (StrUtil.isEmpty(cacheCode) || !StrUtil.equals(captcha, cacheCode)) {
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        if (captcha.equals(cacheCode)) {
            redisTemplate.delete(RedisConstantKey.codeKey + codeKey);
        }

        //1. 查询用户是否存在
        SysUser user = sysUserMapper.selectByUsername(loginDto.getUserName());
        if (user == null) {
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        //2. 校验密码是否正确
        String dbPassword = user.getPassword();
        //加密后的用户密码
        String inputPassword = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        if (!dbPassword.equals(inputPassword)) {
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }

        //3. 生成token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        //4. 将token存入redis,并设置7天过期时间
        redisTemplate.opsForValue().set(RedisConstantKey.userTokenKey + token, JSON.toJSONString(user), 7, TimeUnit.DAYS);
        //5. 返回登录结果
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {
        String result = redisTemplate.opsForValue().get(RedisConstantKey.userTokenKey + token);
        SysUser user = JSON.parseObject(result, SysUser.class);
        return user;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(RedisConstantKey.userTokenKey + token);
    }

    @Override
    public PageInfo<SysUser> list(SysUserDto sysUserDto, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<SysUser> list = sysUserMapper.queryUsersByPage(sysUserDto);
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public Long saveSysUser(SysUser sysUser) {
        String userName = sysUser.getUserName();
        SysUser dbSysUser = sysUserMapper.selectByUsername(userName);
        if (dbSysUser != null) {
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        sysUser.setPassword(DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes()));
        return sysUserMapper.save(sysUser);

    }

    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.update(sysUser);
    }

    @Override
    public void delete(Integer id) {
        sysUserMapper.delete(id);

    }
}

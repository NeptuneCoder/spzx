package com.travel.spzx.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.travel.spzx.common.constant.RedisConstantKey;
import com.travel.spzx.common.exception.GuiguException;
import com.travel.spzx.common.log.annotation.Log;
import com.travel.spzx.manager.mapper.SysUserMapper;
import com.travel.spzx.manager.mapper.SysUserRoleMapper;
import com.travel.spzx.manager.service.SysUserService;
import com.travel.spzx.model.dto.system.AssginRoleDto;
import com.travel.spzx.model.dto.system.LoginDto;
import com.travel.spzx.model.dto.system.SysUserDto;
import com.travel.spzx.model.entity.system.SysUser;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import com.travel.spzx.model.vo.system.LoginVo;
import com.travel.xpzx.utils.AuthContextUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

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

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public LoginVo login(LoginDto loginDto) {
        RedisSerializer<?> keySerializer = redisTemplate.getDefaultSerializer();
        RedisSerializer<?> valueSerializer = redisTemplate.getValueSerializer();
        System.out.println("keySerializer:" + keySerializer.getClass().getName());
        System.out.println("valueSerializer:" + valueSerializer.getClass().getName());
        //增加校验验证码逻辑
        String captcha = loginDto.getCaptcha();
        String codeKey = loginDto.getCodeKey();
        System.out.println("captcha:" + captcha);
        System.out.println("codeKey:" + codeKey);

        String cacheCode = redisTemplate.opsForValue().get(RedisConstantKey.codeKey + codeKey);
        System.out.println("cacheCode:" + cacheCode);
        //如果不相等，要删除
        if (StrUtil.isEmpty(cacheCode) || !StrUtil.equals(captcha, cacheCode)) {
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        if (captcha.equals(cacheCode)) {
            redisTemplate.delete(RedisConstantKey.codeKey + codeKey);
        }

        //1. 查询用户是否存在
        SysUser user = sysUserMapper.selectByUsername(loginDto.getUserName());
        System.out.println("user:" + user);
        if (user == null) {
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }
        //2. 校验密码是否正确
        String dbPassword = user.getPassword();
        //加密后的用户密码
        String inputPassword = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        System.out.println("inputPassword:" + inputPassword);
        System.out.println("dbPassword:" + dbPassword);
        if (!dbPassword.equals(inputPassword)) {
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }

        //3. 生成token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        //4. 将token存入redis,并设置7天过期时间
        redisTemplate.opsForValue().set(RedisConstantKey.USER_TOKEN_KEY + token, JSON.toJSONString(user), 7, TimeUnit.DAYS);
        //5. 返回登录结果
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {
        String result = redisTemplate.opsForValue().get(RedisConstantKey.USER_TOKEN_KEY + token);
        SysUser user = JSON.parseObject(result, SysUser.class);
        return user;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete(RedisConstantKey.USER_TOKEN_KEY + token);
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
        sysUser.setStatus(1);
        return sysUserMapper.save(sysUser);

    }

    @Override
    public void updateSysUser(SysUser sysUser) {
        sysUserMapper.update(sysUser);
    }

    @Override
    public void delete(Integer id) {
        SysUser sysUser = AuthContextUtil.get();

        SysUser dbSysUser = sysUserMapper.queryByUserId(id);
        System.out.println("dbSysUser:" + dbSysUser);
        if (dbSysUser != null && dbSysUser.getUserName().equals(sysUser.getUserName())) {
            throw new GuiguException(ResultCodeEnum.DELETE_SELF_USER_ERROR);
        }
        if (sysUser.getUserName().equals("admin")) {
            throw new GuiguException(ResultCodeEnum.DELETE_ADMIN_USER_ERROR);
        }
        sysUserMapper.delete(id);
    }

    @Log(title = "分配角色", businessType = 0)
    @Transactional
    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {
        //根据userid删除原有角色关系
        sysUserRoleMapper.deleteByUserId(assginRoleDto.getUserId());
        //保存新关系
        for (Long roleId : assginRoleDto.getRoleIdList()) {
            sysUserRoleMapper.save(assginRoleDto.getUserId(), roleId);
        }
    }
}

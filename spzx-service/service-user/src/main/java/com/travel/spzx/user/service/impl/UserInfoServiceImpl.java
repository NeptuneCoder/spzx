package com.travel.spzx.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.travel.spzx.common.constant.RedisConstantKey;
import com.travel.spzx.common.exception.GuiguException;
import com.travel.spzx.model.dto.h5.UserLoginDto;
import com.travel.spzx.model.dto.h5.UserRegisterDto;
import com.travel.spzx.model.entity.user.UserInfo;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import com.travel.spzx.model.vo.h5.UserInfoVo;
import com.travel.spzx.user.mapper.UserInfoMapper;
import com.travel.spzx.user.service.UserInfoService;
import com.travel.xpzx.utils.AuthContextUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(UserRegisterDto userRegisterDto) {
// 获取数据
        String username = userRegisterDto.getUsername();
        String password = userRegisterDto.getPassword();
        String nickName = userRegisterDto.getNickName();
        String code = userRegisterDto.getCode();

        //校验参数
        if (StringUtils.isEmpty(username)
                || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(nickName)
                || StringUtils.isEmpty(code)) {
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }

        //校验校验验证码
        String codeValueRedis = redisTemplate.opsForValue().get(RedisConstantKey.PHONE_CODE_KEY + username);
        if (!code.equals("8888")) {//防止发送验证码，零时处理
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        UserInfo userInfo = userInfoMapper.getByUsername(username);
        if (null != userInfo) {
            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }

        //保存用户信息
        userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setNickName(nickName);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setPhone(username);
        userInfo.setStatus(1);
        userInfo.setSex(0);
        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        userInfoMapper.save(userInfo);

        // 删除Redis中的数据
        redisTemplate.delete(RedisConstantKey.PHONE_CODE_KEY + username);
    }

    //业务接口实现
    @Override
    public String login(UserLoginDto userLoginDto) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        //校验参数
        if (StringUtils.isEmpty(username) ||
                StringUtils.isEmpty(password)) {
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }

        System.out.println("username === " + username);
        UserInfo userInfo = userInfoMapper.getByUsername(username);
        System.out.println("userInfo === " + userInfo);
        if (null == userInfo) {
            throw new GuiguException(ResultCodeEnum.USER_NOT_EXISTS);
        }

        //校验密码
        String md5InputPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        System.out.println("md5InputPassword === " + md5InputPassword);
        if (!md5InputPassword.equals(userInfo.getPassword())) {
            throw new GuiguException(ResultCodeEnum.LOGIN_ERROR);
        }

        //校验是否被禁用
        if (userInfo.getStatus() == 0) {// 0表示禁用 1表示启用
            throw new GuiguException(ResultCodeEnum.ACCOUNT_STOP);
        }

        String token = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set(RedisConstantKey.USER_TOKEN_KEY + token, JSON.toJSONString(userInfo), 30, TimeUnit.DAYS);
        
        return token;
    }

    // com.travel.spzx.user.service.impl.UserInfoServiceImpl
    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
//        String userInfoJSON = redisTemplate.opsForValue().get(RedisConstantKey.USER_TOKEN_KEY + token);
//        if (StringUtils.isEmpty(userInfoJSON)) {
//            throw new GuiguException(ResultCodeEnum.LOGIN_AUTH);
//        }
        UserInfo userInfo = AuthContextUtil.getUserInfo();//JSON.parseObject(userInfoJSON, UserInfo.class);
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        return userInfoVo;
    }
}

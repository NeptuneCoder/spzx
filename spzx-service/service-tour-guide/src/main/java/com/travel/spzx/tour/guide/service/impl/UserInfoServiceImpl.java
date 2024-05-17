package com.travel.spzx.tour.guide.service.impl;

import com.alibaba.fastjson.JSON;
import com.travel.spzx.common.constant.RedisConstantKey;
import com.travel.spzx.common.exception.GuiguException;
import com.travel.spzx.model.dto.h5.ProfileDto;
import com.travel.spzx.model.dto.h5.UserLoginCodeDto;
import com.travel.spzx.model.dto.h5.UserLoginDto;
import com.travel.spzx.model.dto.h5.UserRegisterDto;
import com.travel.spzx.model.entity.tour.TourUserInfo;
import com.travel.spzx.model.entity.user.UserInfo;
import com.travel.spzx.model.vo.common.ResultCodeEnum;
import com.travel.spzx.model.vo.h5.UserInfoVo;

import com.travel.spzx.tour.guide.mapper.UserInfoMapper;
import com.travel.spzx.tour.guide.service.UserInfoService;
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
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(nickName) || StringUtils.isEmpty(code)) {
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }

        //校验校验验证码
        String codeValueRedis = redisTemplate.opsForValue().get(RedisConstantKey.PHONE_CODE_KEY + username);
        if (!code.equals("8888")) {//防止发送验证码，零时处理
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

//        UserInfo userInfo = userInfoMapper.getByUsername(username);
//        if (null != userInfo) {
//            throw new GuiguException(ResultCodeEnum.USER_NAME_IS_EXISTS);
//        }
//
//        //保存用户信息
//        userInfo = new UserInfo();
//        userInfo.setUsername(username);
//        userInfo.setNickName(nickName);
//        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
//        userInfo.setPhone(username);
//        userInfo.setStatus(1);
//        userInfo.setSex(0);
//        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
//        userInfoMapper.save(userInfo);

        // 删除Redis中的数据
        redisTemplate.delete(RedisConstantKey.PHONE_CODE_KEY + username);
    }

    //业务接口实现
    @Override
    public String login(UserLoginDto userLoginDto) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();

        //校验参数
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }


        UserInfo userInfo = null;// userInfoMapper.getByUsername(username);
        if (null == userInfo) {
            throw new GuiguException(ResultCodeEnum.USER_NOT_EXISTS);
        }

        //校验密码
        String md5InputPassword = DigestUtils.md5DigestAsHex(password.getBytes());
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

    /**
     * 扩展验证码登录
     */

    @Override
    public TourUserInfo login(UserLoginCodeDto userLogincodeDto) {
        //校验参数
        if (StringUtils.isEmpty(userLogincodeDto.getCode()) || StringUtils.isEmpty(userLogincodeDto.getPhone())) {
            throw new GuiguException(ResultCodeEnum.DATA_ERROR);
        }
        //校验手机号码是否合法以及验证码位数是否合法
        if (!userLogincodeDto.getPhone().matches("^1[34578]\\d{9}$")) {
            throw GuiguException.build("手机号码不合法");
        }
        if (userLogincodeDto.getCode().length() != 4) {
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        //校验验证码
        //TODO 验证码校验，上线前需要修改
        String codeValueRedis = "8888";//redisTemplate.opsForValue().get(RedisConstantKey.PHONE_CODE_KEY + userLogincodeDto.getPhone());
        if (!userLogincodeDto.getCode().equals(codeValueRedis)) {
            throw new GuiguException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        //从redis中删除验证码
        redisTemplate.delete(RedisConstantKey.PHONE_CODE_KEY + userLogincodeDto.getPhone());
        //校验用户是否存在
        TourUserInfo userInfo = userInfoMapper.getByPhone(userLogincodeDto.getPhone());
        if (null == userInfo) {
            throw new GuiguException(ResultCodeEnum.USER_NOT_EXISTS);
        }
        //校验是否被禁用
        if (userInfo.getAuditStatus() == 1) {// 0表示禁用 1表示启用
            throw new GuiguException(ResultCodeEnum.ACCOUNT_STOP);
        }
        //生成token
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        TourUserInfo userInfoVo = new TourUserInfo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        userInfoVo.setToken(token);
        redisTemplate.opsForValue().set(RedisConstantKey.TOUR_USER_TOKEN_KEY + token, JSON.toJSONString(userInfo), 30, TimeUnit.DAYS);
        return userInfoVo;
    }

    // com.travel.spzx.user.service.impl.UserInfoServiceImpl
    @Override
    public UserInfoVo getCurrentUserInfo() {
//        String userInfoJSON = redisTemplate.opsForValue().get(RedisConstantKey.USER_TOKEN_KEY + token);
//        if (StringUtils.isEmpty(userInfoJSON)) {
//            throw new GuiguException(ResultCodeEnum.LOGIN_AUTH);
//        }
        UserInfo userInfo = AuthContextUtil.getUserInfo();//JSON.parseObject(userInfoJSON, UserInfo.class);
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo, userInfoVo);
        return userInfoVo;
    }

    @Override
    public void updateUserAvatar(String token, TourUserInfo userInfo) {
        //更新redis
        redisTemplate.opsForValue().set(RedisConstantKey.TOUR_USER_TOKEN_KEY + token, JSON.toJSONString(userInfo), 30, TimeUnit.DAYS);
        //更新数据库
        if (null == userInfo) {
            throw new GuiguException(ResultCodeEnum.USER_NOT_EXISTS);
        }
        userInfoMapper.update(userInfo);
    }

    //注销账号
    @Override
    public void closeAccount() {
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        if (null == userInfo) {
            throw new GuiguException(ResultCodeEnum.LOGIN_AUTH);
        }
        System.out.println("closeAccount:" + userInfo);
        userInfo.setStatus(0);
//        userInfoMapper.delete(userInfo);

    }

    @Override
    public void putUserInfo(String token, ProfileDto profileDto) {
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        if (null == userInfo) {
            throw new GuiguException(ResultCodeEnum.LOGIN_AUTH);
        }
        userInfo.setNickName(profileDto.getNickName());
        userInfo.setName(profileDto.getName());
        userInfo.setBirthday(profileDto.getBirthday());
        userInfo.setIdCardType(profileDto.getIdCardType());
        userInfo.setIdCardNo(profileDto.getIdCardNo());
        //更新redis
        redisTemplate.opsForValue().set(RedisConstantKey.USER_TOKEN_KEY + token, JSON.toJSONString(userInfo), 30, TimeUnit.DAYS);
        //更新数据库
//        userInfoMapper.update(userInfo);
    }

    @Override
    public void logout(String token) {
        //删除redis
        System.out.println("logout token:" + token);
        redisTemplate.delete(RedisConstantKey.USER_TOKEN_KEY + token);
        AuthContextUtil.removeUserInfo();
    }
}

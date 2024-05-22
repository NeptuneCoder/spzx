package com.travel.spzx.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.travel.spzx.common.constant.RedisConstantKey;
import com.travel.spzx.model.entity.user.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import com.travel.xpzx.utils.AuthContextUtil;

/**
 * getToken方法中，从redis中获取用户信息，并设置到AuthContextUtil中，供后续业务使用。
 */
@Component
public class UserLoginAuthInterceptor implements HandlerInterceptor {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果token不为空，那么此时验证token的合法性
        String userInfoJSON = redisTemplate.opsForValue().get(RedisConstantKey.USER_TOKEN_KEY + request.getHeader("token"));
        AuthContextUtil.setUserInfo(JSON.parseObject(userInfoJSON, UserInfo.class));
        return true;

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthContextUtil.removeUserInfo();
    }

}
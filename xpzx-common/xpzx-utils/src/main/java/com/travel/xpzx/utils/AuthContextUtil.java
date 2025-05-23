package com.travel.xpzx.utils;

import com.travel.spzx.model.entity.system.SysUser;
import com.travel.spzx.model.entity.tour.TourUserInfo;
import com.travel.spzx.model.entity.user.UserInfo;

public class AuthContextUtil {

    private static final ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>();


    // 定义存储数据的静态方法
    public static void setUserInfo(UserInfo userInfo) {
        userInfoThreadLocal.set(userInfo);
    }

    // 定义获取数据的方法
    public static UserInfo getUserInfo() {
        return userInfoThreadLocal.get();
    }

    // 删除数据的方法
    public static void removeUserInfo() {
        userInfoThreadLocal.remove();
    }


    //导游用户信息
    private static final ThreadLocal<TourUserInfo> tourUserInfoThreadLocal = new ThreadLocal<>();


    // 定义存储数据的静态方法
    public static void setTourUserInfo(TourUserInfo userInfo) {
        tourUserInfoThreadLocal.set(userInfo);
    }

    // 定义获取数据的方法
    public static TourUserInfo getTourUserInfo() {
        return tourUserInfoThreadLocal.get();
    }

    // 删除数据的方法
    public static void removeTourUserInfo() {
        tourUserInfoThreadLocal.remove();
    }

    // 创建一个ThreadLocal对象
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();

    // 定义存储数据的静态方法
    public static void set(SysUser sysUser) {
        threadLocal.set(sysUser);
    }

    // 定义获取数据的方法
    public static SysUser get() {
        return threadLocal.get();
    }

    // 删除数据的方法
    public static void remove() {
        threadLocal.remove();
    }
}

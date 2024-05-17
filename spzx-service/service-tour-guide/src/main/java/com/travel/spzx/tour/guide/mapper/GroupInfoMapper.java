package com.travel.spzx.tour.guide.mapper;

import com.travel.spzx.model.entity.tour.TourUserInfo;
import com.travel.spzx.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GroupInfoMapper {
    UserInfo getByPhone(String phone);

    void update(TourUserInfo userInfo);
}

package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.entity.guide.TourGuideInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TourGuideInfoMapper {
    List<TourGuideInfo> findList(@Param("name") String name, @Param("nickname") String nickname, @Param("phone") String phone, @Param("remark") String remark);

    void save(TourGuideInfo tourGuideInfo);

    List<TourGuideInfo> queryGuideByLevel(String level);

    void delete(Integer id);

    void update(TourGuideInfo tourGuideInfo);

    TourGuideInfo findByNickname(String nickname);

    TourGuideInfo findByTourCertificateNo(String tourCertificateNo);

    TourGuideInfo findByIdTypeNo(String idTypeNo);
}

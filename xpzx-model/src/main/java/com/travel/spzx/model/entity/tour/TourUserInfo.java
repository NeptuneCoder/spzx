package com.travel.spzx.model.entity.tour;

import com.travel.spzx.model.entity.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TourUserInfo extends BaseEntity {
    /**
     * `name` varchar(20) DEFAULT NULL COMMENT '导游名字',
     * `nickname` varchar(20) DEFAULT NULL COMMENT '导游昵称',
     * `phone` varchar(20) DEFAULT NULL COMMENT '导游电话号码',
     * `id_type` varchar(4) DEFAULT NULL COMMENT '证件类型',
     * `id_type_no` varchar(30) DEFAULT NULL COMMENT '身份证号码',
     * `tour_certificate` bigint DEFAULT 0 COMMENT '导游证书[0 没有 1 有]',
     * `tour_certificate_no` varchar(30) DEFAULT NULL COMMENT '导游编号',
     * `level` varchar(4) DEFAULT NULL COMMENT '导游星级，自己定义的导游等级',
     * `aduit_status` bigint DEFAULT 0 COMMENT '当前导游是否认证0,未认证，1 认证通过',
     * `score` decimal(10,2) DEFAULT 0.0 COMMENT '评分',
     * `lead_times` bigint DEFAULT 0 COMMENT '带队次数',
     * `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
     */
    private String name;
    private String nickname;
    private String avatar;
    private String phone;
    private int auditStatus;
    private String idType;
    private String idTypeNo;
    private Long tourCertificate;
    private String tourCertificateNo;
    private String level;
    private BigDecimal score;
    private Long leadTimes;
    private String remark;
    private String token;
}

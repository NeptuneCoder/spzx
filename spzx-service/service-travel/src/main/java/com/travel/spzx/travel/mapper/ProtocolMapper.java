package com.travel.spzx.travel.mapper;

import com.travel.spzx.model.entity.richTxt.RichTxtInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProtocolMapper {
    RichTxtInfo getProtocolByName(@Param("title") String name);
}

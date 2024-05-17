package com.travel.spzx.travel.mapper;

import com.travel.spzx.model.entity.protocol.ProtocolInfo;
import com.travel.spzx.model.entity.richTxt.RichTxtInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProtocolMapper {
    ProtocolInfo getProtocolByName(@Param("title") String name);

    ProtocolInfo getProtocolByKey(@Param("protocolKey") String protocolKey);
}

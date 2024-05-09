package com.travel.spzx.manager.mapper;

import com.travel.spzx.model.entity.protocol.ProtocolInfo;
import com.travel.spzx.model.entity.richTxt.RichTxtInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProtocolRuleMapper {
    void save(ProtocolInfo data);

    void delete(Integer id);

    List<ProtocolInfo> findAll();

    void update(ProtocolInfo cityInfo);

    List<ProtocolInfo> findProtocolInfoByProtocolKey(@Param("protocolKey") String protocolKey);
}

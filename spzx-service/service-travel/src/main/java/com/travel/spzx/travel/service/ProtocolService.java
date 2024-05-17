package com.travel.spzx.travel.service;

import com.travel.spzx.model.vo.protocol.ProtocolVo;

import java.util.List;

public interface ProtocolService {
    String getProtocolByName(String name);

    List<ProtocolVo> getProtocolData(String protocolKey);
}

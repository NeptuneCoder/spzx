package com.travel.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.travel.spzx.model.entity.protocol.ProtocolInfo;
import com.travel.spzx.model.entity.richTxt.RichTxtInfo;

public interface ProtocolRuleService {
    void save(ProtocolInfo data);

    void delete(Integer id);

    PageInfo<ProtocolInfo> findByPage(Integer page, Integer limit);

    void update(ProtocolInfo cityInfo);

}

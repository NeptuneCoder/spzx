package com.travel.spzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.travel.spzx.common.exception.GuiguException;
import com.travel.spzx.manager.mapper.ProtocolRuleMapper;
import com.travel.spzx.manager.mapper.RichTxtInfoMapper;
import com.travel.spzx.manager.service.ProtocolRuleService;
import com.travel.spzx.model.entity.protocol.ProtocolInfo;
import com.travel.spzx.model.entity.richTxt.RichTxtInfo;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProtocolRuleServiceImpl implements ProtocolRuleService {
    @Autowired
    private ProtocolRuleMapper protocolRuleMapper;

    @Override
    public void save(ProtocolInfo data) {
        // TODO Auto-generated method stub
        List<ProtocolInfo> res = protocolRuleMapper.findProtocolInfoByProtocolKey(data.getProtocolKey());
        if (res.size() > 0) {
            throw GuiguException.build("协议key名称重复");
        }
        protocolRuleMapper.save(data);
    }

    @Override
    public void delete(Integer id) {
        protocolRuleMapper.delete(id);
    }

    @Override
    public PageInfo<ProtocolInfo> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<ProtocolInfo> collect = protocolRuleMapper.findAll();
        PageInfo<ProtocolInfo> pageInfo = new PageInfo<>(collect);
        return pageInfo;
    }

    @Override
    public void update(ProtocolInfo data) {
        // TODO Auto-generated method stub
        List<ProtocolInfo> res = protocolRuleMapper.findProtocolInfoByProtocolKey(data.getProtocolKey());
        res.forEach(item -> {
            if (!item.getId().equals(data.getId())) {
                throw GuiguException.build("协议key名称重复");
            }
        });
        protocolRuleMapper.update(data);
    }
}

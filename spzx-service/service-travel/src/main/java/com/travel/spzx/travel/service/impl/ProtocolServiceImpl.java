package com.travel.spzx.travel.service.impl;

import com.travel.spzx.model.entity.richTxt.RichTxtInfo;
import com.travel.spzx.travel.mapper.ProtocolMapper;
import com.travel.spzx.travel.service.ProtocolService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProtocolServiceImpl implements ProtocolService {

    @Autowired
    private ProtocolMapper protocolMapper;

    @Override
    public String getProtocolByName(String name) {
        RichTxtInfo protocolByName = protocolMapper.getProtocolByName(name);
        ;
        String htmlRoot = "<html><head><meta charset=\"UTF-8\">" +
                "<title>" + name + "</title></head><body>";
        String htmlEnd = "</body></html>";
        return htmlRoot + StringEscapeUtils.unescapeXml(protocolByName == null ? "" : StringEscapeUtils.unescapeXml(protocolByName.getContent())) + htmlEnd;
    }

}

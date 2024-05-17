package com.travel.spzx.travel.service.impl;

import com.travel.spzx.model.entity.protocol.ProtocolInfo;
import com.travel.spzx.model.entity.richTxt.RichTxtInfo;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.model.vo.protocol.ProtocolVo;
import com.travel.spzx.travel.mapper.ProtocolMapper;
import com.travel.spzx.travel.mapper.RichTxtMapper;
import com.travel.spzx.travel.service.ProtocolService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProtocolServiceImpl implements ProtocolService {

    @Autowired
    private ProtocolMapper protocolMapper;

    @Autowired
    private RichTxtMapper richTxtMapper;

    @Override
    public String getProtocolByName(String name) {
        RichTxtInfo protocolByName = richTxtMapper.getProtocolByName(name);
        System.out.println(protocolByName);
        String htmlRoot = "<html><head><meta charset=\"UTF-8\">" +
                "<meta charset=\"UTF-8\">" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<title>" + name + "</title></head><body>";
        String htmlEnd = "</body></html>";
        return htmlRoot + StringEscapeUtils.unescapeXml(protocolByName == null ? "" : StringEscapeUtils.unescapeXml(protocolByName.getContent())) + htmlEnd;
    }

    @Override
    public List<ProtocolVo> getProtocolData(String protocolKey) {
        ProtocolInfo protocolByKey = protocolMapper.getProtocolByKey(protocolKey);
        ArrayList<ProtocolVo> protocolVos = new ArrayList<>();
        if (protocolByKey != null) {
            String content = protocolByKey.getContent();
            if (content != null && content.contains(",")) {
                String[] split = content.split(",");

                for (String s : split) {
                    ProtocolVo protocolVo = new ProtocolVo();
                    protocolVo.setName(s);
                    protocolVo.setUrl(protocolByKey.getBaseUrl() + "/" + s);
                    protocolVos.add(protocolVo);
                }
            } else if (content != null && !content.contains(",")) {
                ProtocolVo protocolVo = new ProtocolVo();
                protocolVo.setName(content);
                protocolVo.setUrl(protocolByKey.getBaseUrl() + "/" + content);
                protocolVos.add(protocolVo);
            }
        }
        return protocolVos;
    }

}

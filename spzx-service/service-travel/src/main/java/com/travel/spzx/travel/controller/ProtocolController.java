package com.travel.spzx.travel.controller;

import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.travel.service.ProtocolService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name = "协议相关接口")
@RestController
@RequestMapping("/api/protocol")
public class ProtocolController {
    @Autowired
    private ProtocolService protocolService;

    @GetMapping(value = "detail/{name}")
    public String getProtocolDetail(@PathVariable("name") String name, HttpServletResponse response) {
        System.out.println("name:" + name);
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        String protocolByName = protocolService.getProtocolByName(name);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(protocolByName.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "";
    }
}

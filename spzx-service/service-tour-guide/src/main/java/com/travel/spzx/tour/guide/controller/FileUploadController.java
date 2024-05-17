package com.travel.spzx.tour.guide.controller;


import com.travel.spzx.model.entity.tour.TourUserInfo;
import com.travel.spzx.model.entity.user.UserInfo;
import com.travel.spzx.model.vo.common.Result;

import com.travel.spzx.tour.guide.service.FileUploadService;
import com.travel.spzx.tour.guide.service.UserInfoService;
import com.travel.xpzx.utils.AuthContextUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "文件上传到minio相关接口")
@RestController
@RequestMapping(value = "/api/upload")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    UserInfoService userService;

    @RequestMapping(value = "/fileUpload")
    public Result fileUpload(@RequestParam("file") MultipartFile file) {

        String url = fileUploadService.upload(file);
        return Result.success(url, "文件上传成功");
    }

    @RequestMapping(value = "/member/profile/avatar")
    public Result uploadAvatar(@RequestParam("file") MultipartFile file, @RequestHeader(name = "token") String token) {
        System.out.println("上传文件的token: " + token);
        String url = fileUploadService.upload(file);
        //更新用户的头像信息
        TourUserInfo userInfo = AuthContextUtil.getTourUserInfo();
        userInfo.setAvatar(url);
        userService.updateUserAvatar(token, userInfo);

        return Result.success(url, "文件上传成功");
    }


}

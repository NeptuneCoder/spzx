package com.travel.spzx.travel.controller;


import com.travel.spzx.model.entity.user.UserInfo;
import com.travel.spzx.model.vo.common.Result;
import com.travel.spzx.travel.service.FileUploadService;
import com.travel.spzx.travel.service.UserInfoService;
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

    //这里的名字file 与前端页面中input标签的name属性值一致，在el-element中使用该名字获取上传的文件，
    // 该组件默认的上传文件参数名为file
    //<input type="file" name="file" />
//        @RequestMapping(value = "/fileUpload")
//    public Result fileUpload(MultipartFile file) {
//        fileUploadService.upload();
//        return Result.success();
//    }
    //@RequestParam("file")   上传文件参数名，该名字必须与前端页面中input标签的name属性值一致
    //<input type="file" name="file" />
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
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        userInfo.setAvatar(url);
        userService.updateUserAvatar(token,userInfo);

        return Result.success(url, "文件上传成功");
    }


}

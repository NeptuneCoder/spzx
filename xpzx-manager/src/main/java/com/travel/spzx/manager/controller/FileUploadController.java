package com.travel.spzx.manager.controller;

import com.travel.spzx.manager.service.FileUploadService;
import com.travel.spzx.model.vo.common.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "文件上传到minio相关接口")
@RestController
@RequestMapping(value = "/admin/system")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

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
}

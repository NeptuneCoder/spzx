package com.travel.spzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.travel.spzx.common.exception.GuiguException;
import com.travel.spzx.manager.properties.FileServiceProperties;
import com.travel.spzx.manager.service.FileUploadService;

import com.travel.spzx.model.vo.common.ResultCodeEnum;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Autowired
    private FileServiceProperties fileServiceProperties;


    @Override
    public String upload(MultipartFile file) {
        // TODO Auto-generated method stub
        List<String> fileServiceInfo = fileServiceProperties.getFileServiceInfo();
        if (fileServiceInfo.isEmpty() || fileServiceInfo.size() < 4) {
            throw new GuiguException(ResultCodeEnum.FILE_SERVICE_IP_ERROR);
        }
        try {
            // 创建一个Minio的客户端对象
            String fileServiceUrl = fileServiceInfo.get(0);
            String bucketName = fileServiceInfo.get(1);
            String account = fileServiceInfo.get(2);
            String pwd = fileServiceInfo.get(3);
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(fileServiceUrl)
                    .credentials(account, pwd)
                    .build();

            BucketExistsArgs build = BucketExistsArgs.builder().bucket(bucketName).build();
            boolean found = minioClient.bucketExists(build);

            // 如果不存在，那么此时就创建一个新的桶
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } else {  // 如果存在打印信息
                System.out.println("Bucket 'spzx-bucket' already exists.");
            }
            String originalFilename = file.getOriginalFilename();
            String dateDir = DateUtil.format(DateUtil.date(), "yyyyMMdd");
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String fileName = dateDir + "/" + uuid + originalFilename;

            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    //xpzx-bucket
                    .bucket(bucketName)// 上传到哪个桶
                    .object(fileName)// 上传到桶中的文件名
                    .stream(file.getInputStream(), file.getSize(), -1)// 文件输入流，文件大小，文件元数据
                    .build();
            minioClient.putObject(putObjectArgs);

            // 构建fileUrl
            String fileUrl = fileServiceUrl + "/" + bucketName + "/" + fileName;

            return fileUrl;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuiguException(ResultCodeEnum.FILE_SERVICE_ERROR, e.getMessage());
        }
    }
}

package com.nostratech.academy.minio.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nostratech.academy.minio.config.MinioProperties;
import com.nostratech.academy.minio.dto.MinioFileResponseDTO;
import com.nostratech.academy.minio.service.StorageService;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;

@Service(value = "minio")
@ConditionalOnProperty(value = "app.provider", havingValue = "minio")
public class MinioStorageServiceImpl implements StorageService {

    private final MinioClient minioClient;

    private final MinioProperties minioProperties;

    public MinioStorageServiceImpl(MinioClient minioClient, MinioProperties minioProperties) {
        this.minioClient = minioClient;
        this.minioProperties = minioProperties;
    }

    @Override
    public void uploadFiles(MultipartFile file) throws Exception {

        minioClient.putObject(
                PutObjectArgs.builder().bucket(minioProperties.getBucketName()).object(file.getOriginalFilename()).stream(
                        file.getInputStream(), file.getInputStream().available(), -1)
                        .contentType("video/mp4")
                        .build());

    }

    @Override
    public MinioFileResponseDTO generatePreSignUrl(String fileName, String method) throws Exception {


        GetPresignedObjectUrlArgs args = GetPresignedObjectUrlArgs.builder()
                .method(Method.valueOf(method))
                .bucket(minioProperties.getBucketName())
                .object(fileName)
                .expiry(10, TimeUnit.HOURS)
                .build();
       String url = minioClient.getPresignedObjectUrl(args);


       return new MinioFileResponseDTO(fileName, url);

    }
}

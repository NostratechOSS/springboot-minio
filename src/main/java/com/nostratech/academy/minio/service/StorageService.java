package com.nostratech.academy.minio.service;


import org.springframework.web.multipart.MultipartFile;

import com.nostratech.academy.minio.dto.MinioFileResponseDTO;

public interface StorageService {

    public void uploadFiles(MultipartFile file) throws Exception;

    public MinioFileResponseDTO generatePreSignUrl(String fileName, String method) throws Exception;



}

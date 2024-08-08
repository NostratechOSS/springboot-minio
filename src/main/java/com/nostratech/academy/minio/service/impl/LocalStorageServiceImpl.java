package com.nostratech.academy.minio.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nostratech.academy.minio.dto.MinioFileResponseDTO;
import com.nostratech.academy.minio.service.StorageService;


@Primary
@Service(value = "local")
@ConditionalOnProperty(value = "app.provider", havingValue = "local")
public class LocalStorageServiceImpl implements StorageService {

      private final Path root = Paths.get("uploads");

    @Override
    public void uploadFiles(MultipartFile file) throws IOException {
        Path destination = this.root.resolve(file.getOriginalFilename());
        if (!Files.exists(this.root)) {
            Files.createDirectories(this.root);
        }
        Files.copy(file.getInputStream(), destination);

    }

    @Override
    public MinioFileResponseDTO generatePreSignUrl(String fileName, String method) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generatePreSignUrl'");
    }

}

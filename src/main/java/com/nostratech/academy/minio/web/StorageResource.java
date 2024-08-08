package com.nostratech.academy.minio.web;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nostratech.academy.minio.dto.MinioFileResponseDTO;
import com.nostratech.academy.minio.service.StorageService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
public class StorageResource {


    private final StorageService movieService;

    public StorageResource( StorageService movieService) {
        this.movieService = movieService;
    }
    @PostMapping("/v1/storage")
    public ResponseEntity<Void> uploadMovie(@RequestParam MultipartFile file) throws Exception{
        movieService.uploadFiles(file);
        return ResponseEntity.created(URI.create("/v1/movie")).build();
    }
    
    @GetMapping("/v1/storage")
    public ResponseEntity<MinioFileResponseDTO> generatePresignedUrl(@RequestParam String fileName) throws Exception{
        
        return ResponseEntity.ok(movieService.generatePreSignUrl(fileName, "PUT"));
    }

    
}

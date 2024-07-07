package com.example.bigeventbackend.controller;

import com.example.bigeventbackend.pojo.Result;
import com.example.bigeventbackend.utils.AliOssUtil;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        String fileName = UUID.randomUUID()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String url = AliOssUtil.uploadFile(fileName,file.getInputStream());
        return Result.success(url);
    }
}

package com.lyh.controller;

import com.lyh.domain.ResponseResult;
import com.lyh.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Author:crushlyh
 * Date:2023/3/10 21:06
 */
@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult upload(@RequestParam("img") MultipartFile multipartFile){
        return uploadService.uploading(multipartFile);
    }
}

package com.lyh.controller;

import com.lyh.domain.ResponseResult;
import com.lyh.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Author:crushlyh
 * Date:2023/3/3 22:23
 */
@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @PostMapping("/upload")
    private ResponseResult uploading(MultipartFile img){
        return uploadService.uploading(img);
    }
}

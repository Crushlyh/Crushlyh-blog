package com.lyh.service;

import com.lyh.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.security.PrivateKey;

/**
 * Author:crushlyh
 * Date:2023/3/3 22:26
 */
public interface UploadService {

    ResponseResult uploading(MultipartFile img);
}

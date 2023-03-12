package com.lyh.controller;

import com.lyh.domain.ResponseResult;
import com.lyh.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author:crushlyh
 * Date:2023/2/22 22:44
 */
@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/Alllinks")
    public ResponseResult getAlllinks(){
        return linkService.getAlllinks();
    };
}

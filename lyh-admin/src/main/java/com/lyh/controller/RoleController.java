package com.lyh.controller;

import com.lyh.domain.ResponseResult;
import com.lyh.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author:crushlyh
 * Date:2023/3/12 23:37
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @GetMapping("/list")
    public ResponseResult roleList(){
        return roleService.listAllRole();
    }
}

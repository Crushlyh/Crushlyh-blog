package com.lyh.controller;

import com.lyh.Exception.SystemException;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.dto.AddUserDTO;
import com.lyh.domain.dto.UserDTO;
import com.lyh.domain.entity.User;
import com.lyh.domain.enums.AppHttpCodeEnum;
import com.lyh.service.UserService;
import com.lyh.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author:crushlyh
 * Date:2023/3/13 22:12
 */
@RestController
@RequestMapping("/system/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, User user){
        return userService.listAllUser(pageNum,pageSize,user);
    }

    @PostMapping
    public ResponseResult addUser(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        if (!userService.checkUserNameUnique(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (!userService.checkPhoneUnique(user)){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        if (!userService.checkEmailUnique(user)){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        return userService.addUser(user);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable("id") List<Long> ids){
        if(ids.contains(SecurityUtils.getUserId())){
            return ResponseResult.errorResult(500,"不能删除当前你正在使用的用户");
        }
        return userService.deleteUser(ids);
    }
    @GetMapping("/{id}")
    public ResponseResult getUserInfo(@PathVariable("id")Long id){

        return userService.UserInfo(id);
    }
    @PutMapping
    public ResponseResult updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }
}

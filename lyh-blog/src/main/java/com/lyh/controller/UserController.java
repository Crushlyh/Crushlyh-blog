package com.lyh.controller;

import com.lyh.domain.ResponseResult;
import com.lyh.domain.entity.User;
import com.lyh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author:crushlyh
 * Date:2023/2/26 22:27
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/userInfo")
    public ResponseResult getUserInfo(){

        return userService.getUserInfo();
    }
    @PutMapping("/userInfo")
    public  ResponseResult updateUserInfo(@RequestBody User user) {
        return userService.updateUserInfo(user);
    }
    @PostMapping("/register")
    public ResponseResult registerUser(@RequestBody User user){
        return userService.registerUser(user);
    }
}

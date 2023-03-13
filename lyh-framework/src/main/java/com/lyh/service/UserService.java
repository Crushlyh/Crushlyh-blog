package com.lyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.dto.UserDTO;
import com.lyh.domain.entity.User;

import java.util.List;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-02-25 22:24:18
 */
public interface UserService extends IService<User> {

    ResponseResult getUserInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult registerUser(User user);

    ResponseResult listAllUser(Integer pageNum, Integer pageSize, User user);



    ResponseResult addUser(User user);

    ResponseResult deleteUser(List<Long> ids);

    ResponseResult UserInfo(Long id);

    boolean checkUserNameUnique(String userName);

    boolean checkPhoneUnique(User user);

    boolean checkEmailUnique(User user);

    ResponseResult updateUser(User user);
}


package com.lyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.entity.User;

/**
 * Author:crushlyh
 * Date:2023/2/23 19:49
 */
public interface LoginService extends IService<User> {
    ResponseResult login(User user);

    ResponseResult logout();
}

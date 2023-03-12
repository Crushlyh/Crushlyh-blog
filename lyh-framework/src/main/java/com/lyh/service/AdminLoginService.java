package com.lyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.entity.User;

/**
 * Author:crushlyh
 * Date:2023/3/6 21:28
 */
public interface AdminLoginService extends IService<User> {

    ResponseResult login(User user);

    ResponseResult logout();
}

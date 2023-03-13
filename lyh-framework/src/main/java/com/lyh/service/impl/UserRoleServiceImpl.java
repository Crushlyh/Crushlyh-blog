package com.lyh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.domain.entity.UserRole;
import com.lyh.mapper.UserRoleMapper;
import com.lyh.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * Author:crushlyh
 * Date:2023/3/13 23:06
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}

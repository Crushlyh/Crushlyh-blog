package com.lyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-03-07 22:13:37
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult listAllRole();
}


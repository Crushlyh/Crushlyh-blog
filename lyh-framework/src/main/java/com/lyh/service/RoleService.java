package com.lyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.dto.ChangeRoleDTO;
import com.lyh.domain.dto.RoleDTO;
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

    ResponseResult listAllRole(Integer pageNum, Integer pageSize, RoleDTO roledto);


    ResponseResult changeStatus(ChangeRoleDTO roledto);

    ResponseResult addRole(Role role);

    ResponseResult getRoleInfo(Long id);

    ResponseResult updateRole(Role role);

    ResponseResult deleteRole(Long id);

    List<Role> selectRoleAll();

    List<Long> selectRoleIdByUserId(Long id);
    ResponseResult listAllRole();
}


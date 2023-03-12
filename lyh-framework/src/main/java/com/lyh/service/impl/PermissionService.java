package com.lyh.service.impl;

import com.lyh.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author:crushlyh
 * Date:2023/3/10 23:07
 */
@Service("ps")
public class PermissionService {

    public boolean hasPermission(String permission){
        //判断当前用户是否具有权限
        //如果是超级管理员，返回true
        if(SecurityUtils.isAdmin()){
            return true;
        }
        //否则返回当前用户所具有的权限列表，判断是否具有permission
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }
}

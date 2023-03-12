package com.lyh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.entity.Role;
import com.lyh.mapper.RoleMapper;
import com.lyh.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-03-07 22:13:37
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //如果是管理员 返回集合中只需要有admin
        if(id==1L){
            List<String> roleKeys=new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息

        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult listAllRole() {
        list();
        return ResponseResult.okResult();
    }
}


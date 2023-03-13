package com.lyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.DeleteById;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.Exception.SystemException;
import com.lyh.constants.SystemConstants;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.dto.ChangeRoleDTO;
import com.lyh.domain.dto.RoleDTO;
import com.lyh.domain.entity.Article;
import com.lyh.domain.entity.Role;
import com.lyh.domain.enums.AppHttpCodeEnum;
import com.lyh.domain.vo.PageVO;
import com.lyh.mapper.RoleMapper;
import com.lyh.service.RoleService;
import com.lyh.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    public ResponseResult listAllRole(Integer pageNum, Integer pageSize, RoleDTO roledto) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getStatus,SystemConstants.ROLE_STATUS_NORMAL);
        Page<Role> page=new Page<>(pageNum,pageSize);
        page = page(page, wrapper);
        List<Role> roles = page.getRecords();
        List<RoleDTO> roleDTOS = BeanCopyUtils.copyBeanList(roles, RoleDTO.class);
        PageVO pageVO=new PageVO(roleDTOS,page.getTotal());

        return ResponseResult.okResult(pageVO);
    }

    @Override
    public ResponseResult changeStatus(ChangeRoleDTO roledto) {
        Long id = roledto.getId();
        String status = roledto.getStatus();
        Role role= new Role();
        role.setStatus(status);
        role.setId(id);
        return ResponseResult.okResult(updateById(role));
    }

    @Override
    public ResponseResult addRole(Role role) {
        if(!StringUtils.hasText(role.getRoleName())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(role);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRoleInfo(Long id) {
        Role role = getById(id);
        return ResponseResult.okResult(role);
    }

    @Override
    public ResponseResult updateRole(Role role) {

        updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteRole(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

    @Override
    public List<Role> selectRoleAll() {
        return list(Wrappers.<Role>lambdaQuery().eq(Role::getStatus, SystemConstants.NORMAL));
    }

    @Override
    public List<Long> selectRoleIdByUserId(Long id) {
        return getBaseMapper().selectRoleIdByUserId(id);
    }

    @Override
    public ResponseResult listAllRole() {
        list();
        return ResponseResult.okResult();
    }


}


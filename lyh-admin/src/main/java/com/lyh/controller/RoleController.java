package com.lyh.controller;

import com.lyh.domain.ResponseResult;
import com.lyh.domain.dto.ChangeRoleDTO;
import com.lyh.domain.dto.RoleDTO;
import com.lyh.domain.entity.Role;
import com.lyh.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author:crushlyh
 * Date:2023/3/12 23:37
 */
@RestController
@RequestMapping("/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @GetMapping("/list")
    public ResponseResult roleList(Integer pageNum, Integer pageSize, RoleDTO roledto){
        return roleService.listAllRole(pageNum,pageSize,roledto);
    }
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeRoleDTO roledto){
        return roleService.changeStatus(roledto);
    }
    @PostMapping
    public ResponseResult addRole(@RequestBody Role role){
        return roleService.addRole(role);
    }
    @GetMapping("/{id}")
    public ResponseResult getRoleInfo(@PathVariable("id")Long id){
        return roleService.getRoleInfo(id);
    }
    @PutMapping
    public ResponseResult updateRole(@RequestBody Role role){
        return roleService.updateRole(role);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable("id")Long id){
        return roleService.deleteRole(id);
    }
    @GetMapping("/listAllRole")
    public ResponseResult listAllRole(){

        return roleService.listAllRole();
    }

}

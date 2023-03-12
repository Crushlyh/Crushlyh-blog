package com.lyh.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.lyh.Exception.SystemException;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.entity.LoginUser;
import com.lyh.domain.entity.Menu;
import com.lyh.domain.entity.User;
import com.lyh.domain.enums.AppHttpCodeEnum;
import com.lyh.domain.vo.AdminUserInfoVo;
import com.lyh.domain.vo.RoutersVo;
import com.lyh.domain.vo.UserInfoVO;
import com.lyh.service.AdminLoginService;
import com.lyh.service.LoginService;
import com.lyh.service.MenuService;
import com.lyh.service.RoleService;
import com.lyh.utils.BeanCopyUtils;
import com.lyh.utils.RedisCache;
import com.lyh.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author:crushlyh
 * Date:2023/3/6 21:21
 */
@RestController
public class AdminLoginController {
    @Autowired
    private AdminLoginService adminLoginService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RedisCache redisCache;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //必须要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return adminLoginService.login(user);

    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){

        return adminLoginService.logout();
    }
    @GetMapping("getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //获取当前登录用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id获取用户权限信息
        List<String> perms = menuService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //根据用户id获取角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //封装成VO返回
        UserInfoVO user = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVO.class);
        AdminUserInfoVo adminUserInfoVo=new AdminUserInfoVo(perms,roleKeyList,user);
        return ResponseResult.okResult(adminUserInfoVo);
    }
    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        //查询menu，结果是tree的形式
        Long userId = SecurityUtils.getUserId();
        List<Menu> menus=menuService.selectMenuTreeByUserId(userId);
        //封装数据返回
        return ResponseResult.okResult(new RoutersVo(menus));
    }

}

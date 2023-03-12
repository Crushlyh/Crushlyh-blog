package com.lyh.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.entity.LoginUser;
import com.lyh.domain.entity.User;
import com.lyh.domain.vo.UserInfoVO;
import com.lyh.domain.vo.UserLoginVO;
import com.lyh.mapper.UserMapper;
import com.lyh.service.AdminLoginService;
import com.lyh.utils.BeanCopyUtils;
import com.lyh.utils.JwtUtil;
import com.lyh.utils.RedisCache;
import com.lyh.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Author:crushlyh
 * Date:2023/3/6 21:29
 */
@Service("AdminloginService")
public class AdminLoginServiceImpl extends ServiceImpl<UserMapper,User> implements AdminLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authentication)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid，生成token
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();//获取认证的主体，强转为LoginUser
        String userId = loginUser.getUser().getId().toString();

        String jwt = JwtUtil.createJWT(userId);//利用JWT生成token

        //把用户信息存入redis
        redisCache.setCacheObject("Adminlogin:"+userId,loginUser);
        //把token封装 返回
        Map<String,String> map =new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        //获取当前登录的用户id
        Long userId = SecurityUtils.getUserId();
        //删除redis中的值
        redisCache.deleteObject("Adminlogin:"+userId);
        return ResponseResult.okResult();
    }
}

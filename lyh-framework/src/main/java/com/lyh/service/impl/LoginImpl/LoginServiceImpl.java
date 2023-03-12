package com.lyh.service.impl.LoginImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.entity.LoginUser;
import com.lyh.domain.entity.User;
import com.lyh.domain.vo.UserInfoVO;
import com.lyh.domain.vo.UserLoginVO;
import com.lyh.mapper.UserMapper;
import com.lyh.service.LoginService;
import com.lyh.utils.BeanCopyUtils;
import com.lyh.utils.JwtUtil;
import com.lyh.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Author:crushlyh
 * Date:2023/2/23 19:51
 */
@Service("LoginService")
public class LoginServiceImpl extends ServiceImpl<UserMapper,User> implements LoginService {
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
        //将User转换为UserInfo
        UserInfoVO userInfoVO = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVO.class);
        //把用户信息存入redis
        redisCache.setCacheObject("login:"+userId,loginUser);
        //把token和userInfo封装 返回
        UserLoginVO userLoginVO=new UserLoginVO(jwt,userInfoVO);

        return ResponseResult.okResult(userLoginVO);
    }

    @Override
    public ResponseResult logout() {
        //获取token
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //解析获取userId
        Long userId = loginUser.getUser().getId();
        //删除redis当中的用户信息
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult();
    }
}

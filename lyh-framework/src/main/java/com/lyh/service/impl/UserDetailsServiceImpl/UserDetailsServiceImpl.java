package com.lyh.service.impl.UserDetailsServiceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lyh.constants.SystemConstants;
import com.lyh.domain.entity.LoginUser;
import com.lyh.domain.entity.User;
import com.lyh.mapper.MenuMapper;
import com.lyh.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Author:crushlyh
 * Date:2023/2/23 20:01
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<User> userWrapper=new LambdaQueryWrapper<>();
        userWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(userWrapper);
        //判断是否查到用户
            //如果没有，抛出异常
            if(Objects.isNull(user)){
                throw new RuntimeException("用户不存在");
            }
        //查询到用户，返回用户信息
        if(user.getType().equals(SystemConstants.ADMAIN)){
            List<String> list = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user,list);
        }
        return new LoginUser(user,null);
    }
}

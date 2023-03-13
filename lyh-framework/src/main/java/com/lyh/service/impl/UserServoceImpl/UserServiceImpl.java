package com.lyh.service.impl.UserServoceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.Exception.SystemException;
import com.lyh.constants.SystemConstants;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.dto.UserDTO;
import com.lyh.domain.entity.Role;
import com.lyh.domain.entity.User;
import com.lyh.domain.entity.UserRole;
import com.lyh.domain.enums.AppHttpCodeEnum;
import com.lyh.domain.vo.PageVO;
import com.lyh.domain.vo.UserInfoAndRoleIdsVo;
import com.lyh.domain.vo.UserInfoVO;
import com.lyh.domain.vo.UserVo;
import com.lyh.mapper.UserMapper;
import com.lyh.service.RoleService;
import com.lyh.service.UserRoleService;
import com.lyh.service.UserService;
import com.lyh.utils.BeanCopyUtils;
import com.lyh.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-02-25 22:24:19
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Override
    public ResponseResult getUserInfo() {
        //获取用户当前Id
        Long userId = SecurityUtils.getUserId();
        //根据Id查询信息
        User user = getById(userId);
        //封装成UserVO
        UserInfoVO userInfoVO = BeanCopyUtils.copyBean(user, UserInfoVO.class);
        return ResponseResult.okResult(userInfoVO);
    }
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult registerUser(User user) {
        //判断数据是否为空
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);//用户名
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);//密码
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);//邮箱
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);//昵称
        }
        //数据是否已经存在
        if(userNameExit(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExit(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        if(emailExit(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //对密码加密
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        //存入数据库中
        save(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllUser(Integer pageNum, Integer pageSize, User user) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.like(StringUtils.hasText(user.getUserName()),User::getUserName,user.getUserName());
        queryWrapper.eq(StringUtils.hasText(user.getStatus()),User::getStatus,user.getStatus());
        queryWrapper.eq(StringUtils.hasText(user.getPhonenumber()),User::getPhonenumber,user.getPhonenumber());

        Page<User> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);

        //转换成VO
        List<User> users = page.getRecords();
        List<UserVo> userVoList = users.stream()
                .map(u -> BeanCopyUtils.copyBean(u, UserVo.class))
                .collect(Collectors.toList());
        PageVO pageVO = new PageVO();
        pageVO.setTotal(page.getTotal());
        pageVO.setRows(userVoList);
        return ResponseResult.okResult(pageVO);
    }



    @Override
    public ResponseResult addUser(User user) {
        //密码加密处理
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);

        if(user.getRoleIds()!=null&&user.getRoleIds().length>0){
            insertUserRole(user);
        }
        return ResponseResult.okResult();

    }

    @Override
    @Transactional
    public ResponseResult deleteUser(List<Long> ids) {
        removeByIds(ids);
        return ResponseResult.okResult();
    }

    @Autowired
    private RoleService roleService;
    @Override
    public ResponseResult UserInfo(Long id) {
        List<Role> roles = roleService.selectRoleAll();
        User user = getById(id);
        List<Long> roleIds = roleService.selectRoleIdByUserId(id);
        UserInfoAndRoleIdsVo vo = new UserInfoAndRoleIdsVo(user,roles,roleIds);
        return ResponseResult.okResult(vo);
    }

    @Override
    public boolean checkUserNameUnique(String userName) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getUserName,userName))==0;

    }

    @Override
    public boolean checkPhoneUnique(User user) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getPhonenumber,user.getPhonenumber()))==0;

    }

    @Override
    public boolean checkEmailUnique(User user) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getEmail,user.getEmail()))==0;

    }

    @Override
    public ResponseResult updateUser(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }



    @Autowired
    private UserRoleService userRoleService;

    private void insertUserRole(User user) {
        List<UserRole> sysUserRoles = Arrays.stream(user.getRoleIds())
                .map(roleId -> new UserRole(user.getId(), roleId)).collect(Collectors.toList());
        userRoleService.saveBatch(sysUserRoles);
    }

    //username
    boolean userNameExit(String userName){
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper)>0?true:false;
    }
    //nickname
    boolean nickNameExit(String nickName){
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(User::getNickName,nickName);
        return count(queryWrapper)>0?true:false;
    }
    //email
    boolean emailExit(String email){
        LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(User::getUserName,email);
        return count(queryWrapper)>0?true:false;
    }
}


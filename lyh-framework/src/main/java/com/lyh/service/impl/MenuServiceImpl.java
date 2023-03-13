package com.lyh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.constants.SystemConstants;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.entity.Menu;
import com.lyh.domain.vo.MenuTreeVo;
import com.lyh.domain.vo.RoleMenuTreeSelectVo;
import com.lyh.mapper.MenuMapper;
import com.lyh.service.MenuService;
import com.lyh.utils.BeanCopyUtils;
import com.lyh.utils.SystemConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-03-07 22:06:57
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {
    @Autowired
    private MenuService menuService;

    @Override
    public List<String> selectRoleKeyByUserId(long userId) {
        //如果是管理员否则所有权限，否则返回其具有的权限
        if(userId==SystemConstants.GM_ID){
            LambdaQueryWrapper<Menu> queryWrapper=new LambdaQueryWrapper();
            queryWrapper.in(Menu::getMenuType, SystemConstants.MENU,SystemConstants.BUTTEN);
            queryWrapper.eq(Menu::getStatus,SystemConstants.ARRTICLE_STATUS_NORMAL);
            List<Menu> menus = list(queryWrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }

        return getBaseMapper().selectPermsByUserId(userId);
    }

    @Override
    public List<Menu> selectMenuTreeByUserId(long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        //判断是否是管理员
        //如果是 返回所有符合要求的Menu
        if(userId==SystemConstants.GM_ID){
            menus=menuMapper.selectAllRouterMenu();

        }else{
            //否则返回当前用户所具有的的Menu
           menus=menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        List<Menu> menuTree=buildMenuTree(menus,0L);
        return menuTree;


    }

    @Override
    public ResponseResult listMenuTree(Menu menu) {
        LambdaQueryWrapper<Menu> wrapper= new LambdaQueryWrapper();//LambdaQueryWrapper需要加上类型
        wrapper.like(StringUtils.hasText(menu.getMenuName()),Menu::getMenuName,menu.getMenuName());
        wrapper.eq(StringUtils.hasText(menu.getStatus()),Menu::getStatus,menu.getStatus());
        //排序
        wrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
        List<Menu> menus = list(wrapper);
        return ResponseResult.okResult(menus);
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectMenuById(Long id) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper();
        wrapper.eq(Menu::getId,id);
        List<Menu> menus = list(wrapper);
        return ResponseResult.okResult(menus);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        if(menu.getId().equals(menu.getParentId())){
            return ResponseResult.errorResult(500,"修改菜单："+menu.getMenuName()+"失败，上级菜单不能选择自己");
        }
        save(menu);
        return ResponseResult.okResult();
    }


    @Override
    public boolean hasChild(Long id) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId,id);
        return count(wrapper)!=0;
    }

    @Override
    public ResponseResult getTree() {
        Menu menu = new Menu();
        ResponseResult<List<Menu>> result = listMenuTree(menu);
        List<Menu> menus = result.getData();
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        return ResponseResult.okResult(menuTreeVos);
    }

    @Override
    public ResponseResult roleMenuTreeselect(Long id) {
        ResponseResult<List<Menu>> result = listMenuTree(new Menu());
        List<Menu> menus = result.getData();
        ResponseResult<List<Long>> result1 = menuService.selectMenuById(id);
        List<Long> checkeKeys = result1.getData();
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        RoleMenuTreeSelectVo roleMenuTreeSelectVo = new RoleMenuTreeSelectVo(checkeKeys, menuTreeVos);
        return ResponseResult.okResult(roleMenuTreeSelectVo);
    }

    private List<Menu> buildMenuTree(List<Menu> menus,Long parentId) {
        List<Menu> menuTree = menus.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }
    /**
     * 获取传入参数的子类
     * @Param menu
     *
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus){
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m->m.setChildren(getChildren(m,menus)))
                .collect(Collectors.toList());
        return childrenList;
    }


}


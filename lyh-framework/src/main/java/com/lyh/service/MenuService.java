package com.lyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-03-07 22:06:57
 */
public interface MenuService extends IService<Menu> {

    List<String> selectRoleKeyByUserId(long userId);

    List<Menu> selectMenuTreeByUserId(long userId);

    ResponseResult listMenuTree(Menu menu);

    ResponseResult addMenu(Menu menu);

    ResponseResult selectMenuById(Long id);

    ResponseResult updateMenu(Menu menu);

    boolean hasChild(Long id);

    ResponseResult getTree();

    ResponseResult roleMenuTreeselect(Long id);
}


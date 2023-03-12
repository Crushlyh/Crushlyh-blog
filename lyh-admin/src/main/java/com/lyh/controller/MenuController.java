package com.lyh.controller;

import com.lyh.domain.ResponseResult;
import com.lyh.domain.entity.Menu;
import com.lyh.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author:crushlyh
 * Date:2023/3/12 20:44
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;
    @GetMapping("/list")
    public ResponseResult list(Menu menu){
        return menuService.listMenuTree(menu);
    }
    @PostMapping
    public ResponseResult add(@RequestBody Menu menu){
        return menuService.addMenu(menu);
    }
    @GetMapping("/{menuId}")
    public ResponseResult selectMenuById(@PathVariable("menuId") Long menuId){
        return menuService.selectMenuById(menuId);
    }
    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }
    @DeleteMapping("/{menuId}")
    public ResponseResult deleteMenu(@PathVariable("menuId")Long id){
        if (menuService.hasChild(id)) {
            return ResponseResult.errorResult(500,"存在子菜单不允许删除");
        }
        menuService.removeById(id);
        return ResponseResult.okResult();
    }
}

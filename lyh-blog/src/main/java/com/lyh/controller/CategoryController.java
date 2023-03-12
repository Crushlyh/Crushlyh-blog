package com.lyh.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.entity.Category;
import com.lyh.service.CategoryService;
import com.lyh.domain.entity.Category;
import com.lyh.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 分类表(Category)表控制层
 *
 * @author makejava
 * @since 2023-02-20 17:52:17
 */
@RestController
@RequestMapping("/category")
public class CategoryController{
    /**
     * 服务对象
     */
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }


}


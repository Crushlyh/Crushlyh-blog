package com.lyh.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.excel.EasyExcel;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.dto.AdminCategoryDTO;
import com.lyh.domain.entity.Category;
import com.lyh.domain.enums.AppHttpCodeEnum;
import com.lyh.domain.vo.CategoryVO;
import com.lyh.domain.vo.ExcelCategoryVo;
import com.lyh.service.CategoryService;
import com.lyh.utils.BeanCopyUtils;
import com.lyh.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Author:crushlyh
 * Date:2023/3/10 20:35
 */
@RestController
@RequestMapping("/content/category")
public class AdminCategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }
    @PreAuthorize("@ps.hasPermission('content:category:export')")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        try {
            //设置下载文件的请求头
            WebUtils.setDownLoadHeader("分类.xlsx",response);
            //获取需要导出的数据
            List<Category> categoryVos = categoryService.list();

            List<ExcelCategoryVo> excelCategoryVos = BeanCopyUtils.copyBeanList(categoryVos, ExcelCategoryVo.class);
            //把数据写入到Excel中
            EasyExcel.write(response.getOutputStream(), ExcelCategoryVo.class).autoCloseStream(Boolean.FALSE).sheet("分类导出")
                    .doWrite(excelCategoryVos);

        } catch (Exception e) {
            //如果出现异常也要响应json
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
            WebUtils.renderString(response, JSON.toJSONString(result));
        }
    }

    @GetMapping("/list")
    public ResponseResult selectAll(Integer pageNum, Integer pageSize, Category category){
        return categoryService.selectAllCategory(pageNum,pageSize,category);
    }
    @PostMapping
    public ResponseResult addCategory(@RequestBody Category category){
        return categoryService.addCategory(category);
    }
    @GetMapping("/{id}")
    public ResponseResult selectCategoryById(@PathVariable("id")Long id){
        return categoryService.selectCategoryById(id);
    }
    @PutMapping
    public ResponseResult updateCategory(@RequestBody Category category){
        return categoryService.updateCategory(category);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteCategory(@PathVariable("id")List<Long> ids){
        return categoryService.deleteCategory(ids);
    }
}

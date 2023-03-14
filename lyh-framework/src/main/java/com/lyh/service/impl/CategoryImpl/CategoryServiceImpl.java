package com.lyh.service.impl.CategoryImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.constants.SystemConstants;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.dto.AdminCategoryDTO;
import com.lyh.domain.entity.Article;
import com.lyh.domain.entity.Category;
import com.lyh.domain.vo.CategoryVO;
import com.lyh.domain.vo.PageVO;
import com.lyh.mapper.CategoryMapper;
import com.lyh.service.ArticleService;
import com.lyh.service.CategoryService;
import com.lyh.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-02-20 17:52:19
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private ArticleService articleService;
    @Override
    public ResponseResult getCategoryList() {
        //查询文章表，状态为已发表
        LambdaQueryWrapper<Article> articleWrapper=new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARRTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章分类id，去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories= categories.stream().filter(category -> category.getStatus().equals(SystemConstants.CATEGORY_STATUS_NORMAL))
                .collect(Collectors.toList());
        //封装VO

        List<CategoryVO> categoryVOS = BeanCopyUtils.copyBeanList(categories, CategoryVO.class);

        return ResponseResult.okResult(categoryVOS);
    }

    @Override
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> wrapper=new LambdaQueryWrapper();
        wrapper.eq(Category::getStatus,SystemConstants.ARRTICLE_STATUS_NORMAL);
        List<Category> categories = list(wrapper);
        List<CategoryVO> categoryVOS = BeanCopyUtils.copyBeanList(categories, CategoryVO.class);
        return ResponseResult.okResult(categoryVOS);
    }

    @Override
    public ResponseResult selectAllCategory(Integer pageNum, Integer pageSize, Category category) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(category.getName()),Category::getName,category.getName());
        wrapper.eq(StringUtils.hasText(category.getStatus()),Category::getStatus,category.getStatus());
        Page<Category> page = new Page<>();
        page=page(page,wrapper);
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        List<Category> categories = page.getRecords();
        List<CategoryVO> categoryVOS = BeanCopyUtils.copyBeanList(categories, CategoryVO.class);
        PageVO pageVO = new PageVO();
        pageVO.setTotal(page.getTotal());
        pageVO.setRows(categoryVOS);
        return ResponseResult.okResult(pageVO);
    }

    @Override
        public ResponseResult addCategory(Category category) {
        save(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectCategoryById(Long id) {
        Category category = getById(id);
        return ResponseResult.okResult(category);
    }

    @Override
    public ResponseResult updateCategory(Category category) {
        updateById(category);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteCategory(List<Long> ids) {
        removeByIds(ids);
        return ResponseResult.okResult();
    }


}


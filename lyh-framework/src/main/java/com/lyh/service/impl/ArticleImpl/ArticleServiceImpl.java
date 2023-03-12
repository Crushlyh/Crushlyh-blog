package com.lyh.service.impl.ArticleImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.DeleteById;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.constants.SystemConstants;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.dto.AddArticleDTO;
import com.lyh.domain.dto.ArticleDTO;
import com.lyh.domain.entity.Article;
import com.lyh.domain.entity.ArticleTag;
import com.lyh.domain.entity.Category;
import com.lyh.domain.vo.*;
import com.lyh.mapper.ArticleMapper;
import com.lyh.service.ArticleService;
import com.lyh.service.ArticleTagService;
import com.lyh.service.CategoryService;
import com.lyh.utils.BeanCopyUtils;
import com.lyh.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Author:crushlyh
 * Date:2023/2/18 20:59
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章，封装成ResponseResult返回
        LambdaQueryWrapper<Article> queryWrapper=new LambdaQueryWrapper<>();//封装查询条件
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemConstants.ARRTICLE_STATUS_NORMAL);
        //按照浏览量进行降序排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //浏览量前十
        Page<Article> page=new Page<>(1,10);
        page(page,queryWrapper);
        List<Article> articles = page.getRecords();
        //Bean拷贝，将Article对象封装成ArticleVo

        /*for (Article article : articles) {
            HotArticleVo vo=new HotArticleVo();
            BeanUtils.copyProperties(article,vo);
            articleVos.add(vo);
        }*/

        //优化
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);

        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> articleWrapper=new LambdaQueryWrapper<>();
        //如果有catagoryId
        articleWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //状态是正式发布
        articleWrapper.eq(Article::getStatus,SystemConstants.ARRTICLE_STATUS_NORMAL);
        //置顶文章显示在最前面，对isTop进行排序
        articleWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> page=new Page<>(pageNum,pageSize);
        page = page(page, articleWrapper);

        List<Article> articles = page.getRecords();
        articles= articles.stream()
                .map(new Function<Article, Article>() {

                    @Override
                    public Article apply(Article article) {
                        Category category = categoryService.getById(article.getCategoryId());
                        article.setCategoryName(category.getName());
                        return article;
                    }
                }).collect(Collectors.toList());
        //封装查询结果
        List<ArticleListVO> articleListVOS = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVO.class);
        PageVO pageVO=new PageVO(articleListVOS,page.getTotal());
        return ResponseResult.okResult(pageVO);
    }

    @Override
    public ResponseResult getArticleDetail(Long id){
        //根据id查询文章
        Article article = getById(id);
        //从Redis中获取viewcount
        Integer viewcount = redisCache.getCacheMapValue("article:viewCount", id.toString());
        article.setViewCount(viewcount.longValue());
        //转换成vo
        ArticleDetailVO articleDetailVO = BeanCopyUtils.copyBean(article, ArticleDetailVO.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVO.getCategoryId();
        Category category = categoryService.getById(categoryId);
        //封装 返回
        if(category!=null){
            articleDetailVO.setCategoryName(category.getName());
        }
        ResponseResult result = ResponseResult.okResult(articleDetailVO);
        return result;
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应id的viewcount
        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult add(AddArticleDTO addArticleDTO) {
        Article article = BeanCopyUtils.copyBean(addArticleDTO, Article.class);
        save(article);
        List<ArticleTag> articleTags = addArticleDTO.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId))
                .collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectarticleList(Article article, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper();//需要定义类型
        wrapper.like(StringUtils.hasText(article.getTitle()),Article::getTitle,article.getTitle());
        wrapper.like(StringUtils.hasText(article.getSummary()),Article::getSummary,article.getSummary());
        Page<Article> page= new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,wrapper);

        List<Article> articles = page.getRecords();
        PageVO pageVO=new PageVO();
        pageVO.setRows(articles);
        pageVO.setTotal(page.getTotal());
        return ResponseResult.okResult(pageVO);
    }

    @Override
    public ArticleVO getInfo(Long id) {
        Article article = getById(id);
        //获取关联标签
        LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleTagLambdaQueryWrapper.eq(ArticleTag::getArticleId,article.getId());
        List<ArticleTag> articleTags = articleTagService.list(articleTagLambdaQueryWrapper);
        List<Long> tags = articleTags.stream().map(articleTag -> articleTag.getTagId()).collect(Collectors.toList());

        ArticleVO articleVo = BeanCopyUtils.copyBean(article,ArticleVO.class);
        articleVo.setTags(tags);
        return articleVo;
    }

    @Override
    public ResponseResult updateArticle(ArticleDTO articleDto) {
        Article article = BeanCopyUtils.copyBean(articleDto, Article.class);
        updateById(article);
        //删除原有的 标签和博客的关联
        LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleTagLambdaQueryWrapper.eq(ArticleTag::getArticleId,article.getId());
        articleTagService.remove(articleTagLambdaQueryWrapper);
        //添加新的博客和标签的关联信息
        List<ArticleTag> articleTags = articleDto.getTags().stream()
                .map(tagId -> new ArticleTag(articleDto.getId(), tagId))
                .collect(Collectors.toList());
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteArticle(Long id) {
        removeById(id);
        return ResponseResult.okResult();
    }

}

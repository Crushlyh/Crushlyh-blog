package com.lyh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.dto.AddArticleDTO;
import com.lyh.domain.dto.ArticleDTO;
import com.lyh.domain.entity.Article;
import com.lyh.domain.vo.ArticleVO;

/**
 * Author:crushlyh
 * Date:2023/2/18 20:57
 */
public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult add(AddArticleDTO addArticleDTO);

    ResponseResult selectarticleList(Article article, Integer pageNum, Integer pageSize);

    ArticleVO getInfo(Long id);

    ResponseResult updateArticle(ArticleDTO articleDTO);

    ResponseResult deleteArticle(Long id);
}

package com.lyh.controller;

import com.lyh.domain.ResponseResult;
import com.lyh.domain.dto.AddArticleDTO;
import com.lyh.domain.dto.ArticleDTO;
import com.lyh.domain.entity.Article;
import com.lyh.domain.vo.ArticleVO;
import com.lyh.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author:crushlyh
 * Date:2023/3/10 21:09
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDTO addArticleDTO){
        return articleService.add(addArticleDTO);
    }

    @GetMapping("/list")
    public ResponseResult list(Article article,Integer pageNum, Integer pageSize){
        return articleService.selectarticleList(article,pageNum,pageSize);
    }
    @GetMapping("/{id}")
    public ResponseResult selectArticle(@PathVariable("id") Long id){
        ArticleVO articleVO = articleService.getInfo(id);
        return ResponseResult.okResult(articleVO);
    }
    @PutMapping
    public ResponseResult updateArticle(@RequestBody ArticleDTO articleDTO){

        return articleService.updateArticle(articleDTO);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteArticle(@PathVariable("id") Long id){
        return articleService.deleteArticle(id);
    }
}

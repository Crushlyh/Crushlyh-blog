package com.lyh.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Author:crushlyh
 * Date:2023/2/22 22:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVO {

    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //所属分类id
    private Long categoryId;
    private String categoryName;
    //是否允许评论 1是，0否
    private String isComment;
    //访问量
    private Long viewCount;
    private Date createTime;
}

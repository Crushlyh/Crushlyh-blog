package com.lyh.controller;



import com.baomidou.mybatisplus.extension.api.ApiController;
import com.lyh.constants.SystemConstants;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.dto.AddCommentDto;
import com.lyh.domain.entity.Comment;
import com.lyh.domain.enums.AppHttpCodeEnum;
import com.lyh.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 评论表(Comment)表控制层
 *
 * @author makejava
 * @since 2023-02-25 21:42:19
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "评论",description="评论相关接口")
public class CommentController extends ApiController {
    /**
     * 服务对象
     */
    @Autowired
    private CommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId,Integer pageNum,Integer pageSize) {
        return commentService.commentList(SystemConstants.ARRTICLE_COMMENT,articleId,pageNum,pageSize);
    }
    @PostMapping
    public ResponseResult addcomment(@RequestBody AddCommentDto comment){
        return commentService.addcomment(comment);
    }
    @GetMapping("/linkCommentList")
    @ApiOperation(value ="友链评论列表",notes = "获取友链评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name="pageNum",value="页号"),
            @ApiImplicitParam(name="pageSize",value = "页面大小")
    }
    )
    public ResponseResult commentList(Integer pageNum,Integer pageSize) {
        return commentService.commentList(SystemConstants.LINK_COMMENT,null,pageNum,pageSize);
    }

}


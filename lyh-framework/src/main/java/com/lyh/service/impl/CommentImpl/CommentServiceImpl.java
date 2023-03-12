package com.lyh.service.impl.CommentImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyh.Exception.SystemException;
import com.lyh.constants.SystemConstants;
import com.lyh.domain.ResponseResult;
import com.lyh.domain.dto.AddCommentDto;
import com.lyh.domain.entity.Comment;
import com.lyh.domain.enums.AppHttpCodeEnum;
import com.lyh.domain.vo.CommentVO;
import com.lyh.domain.vo.PageVO;
import com.lyh.mapper.CommentMapper;
import com.lyh.service.CommentService;
import com.lyh.service.UserService;
import com.lyh.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-02-25 21:42:23
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;
    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        //查询对应文章根评论

        //对articleId进行判断
        LambdaQueryWrapper<Comment> commentWrapper=new LambdaQueryWrapper<>();
        commentWrapper.eq(SystemConstants.ARRTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);
        //根评论rootId=-1
        commentWrapper.eq(Comment::getRootId, SystemConstants.ROOT_ID_NORMAL);

        //评论类型
        commentWrapper.eq(Comment::getType,commentType);
        //分页查询
        Page<Comment> page=new Page<Comment>(pageNum,pageSize);
        page(page,commentWrapper);
        List<Comment> comments = page.getRecords();
        List<CommentVO> commentVoList =toCommentVoList(comments);
        //查询根评论对应子评论集合
        for (CommentVO commentVo : commentVoList) {
            //查询对应的子评论
        List<CommentVO> children = getChildren(commentVo.getId());
            //赋值
            commentVo.setChildren(children);
        }
        return ResponseResult.okResult(new PageVO(commentVoList,page.getTotal()));
    }

    @Override
    public ResponseResult addcomment(AddCommentDto addCommentDto) {
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        //评论不能为空
        if(!StringUtils.hasText(addCommentDto.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    private List<CommentVO> getChildren(Long id) {

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);

        List<CommentVO> commentVos = toCommentVoList(comments);
        return commentVos;
    }
    private List<CommentVO> toCommentVoList(List<Comment> list){
        List<CommentVO> commentVos = BeanCopyUtils.copyBeanList(list, CommentVO.class);
        //遍历vo集合
        for (CommentVO commentVo : commentVos) {
            //通过creatyBy查询用户的昵称并赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            //通过toCommentUserId查询用户的昵称并赋值
            //如果toCommentUserId不为-1才进行查询
            if(commentVo.getToCommentUserId()!=-1){
                String toCommentUserName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentUserName);
            }
        }
        return commentVos;
    }


}


package com.gameForum.service;

import com.gameForum.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gameForum.entity.CommentDto;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yeshang18
 * @since 2023-04-21
 */
public interface CommentService extends IService<Comment> {
    List<CommentDto> getCommentOrderByTime(Integer articleId, Integer pageNo, Integer pageSize, Integer userId);

    List<CommentDto> getCommentOrderByLikes(Integer articleId,Integer pageNo,Integer pageSize,Integer userId);

    List<CommentDto> getChildrenComments(Integer articleId,Integer pageNo,Integer pageSize,Integer userId);
}

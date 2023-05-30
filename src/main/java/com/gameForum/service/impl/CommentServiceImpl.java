package com.gameForum.service.impl;

import com.gameForum.entity.Comment;
import com.gameForum.entity.CommentDto;
import com.gameForum.mapper.CommentMapper;
import com.gameForum.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yeshang18
 * @since 2023-04-21
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Override
    public List<CommentDto> getCommentOrderByTime(Integer articleId, Integer pageNo, Integer pageSize, Integer userId) {
        return commentMapper.selectCommentOrderByTime(articleId, pageNo, pageSize, userId);
    }

    @Override
    public List<CommentDto> getCommentOrderByLikes(Integer articleId, Integer pageNo, Integer pageSize, Integer userId) {
        return commentMapper.selectCommentOrderByLikes(articleId, pageNo, pageSize, userId);
    }

    @Override
    public List<CommentDto> getChildrenComments(Integer commentId, Integer userId) {
        return commentMapper.selectChildrenComments(commentId, userId);
    }

    @Override
    public CommentDto getCommentById(Integer id, Integer userId) {
        return commentMapper.selectCommentById(id,userId);
    }
}

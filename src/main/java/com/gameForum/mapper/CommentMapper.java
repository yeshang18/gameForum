package com.gameForum.mapper;

import com.gameForum.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gameForum.entity.CommentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yeshang18
 * @since 2023-04-21
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    List<CommentDto> selectCommentOrderByTime(Integer articleId,Integer pageNo,Integer pageSize,Integer userId);

    List<CommentDto> selectCommentOrderByLikes(Integer articleId,Integer pageNo,Integer pageSize,Integer userId);

    List<CommentDto> selectChildrenComments(Integer articleId,Integer pageNo,Integer pageSize,Integer userId);
}

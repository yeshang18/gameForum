package com.gameForum.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author yeshang18
 * @since 2023-04-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //评论帖子ID
    private Integer articleId;

    //评论评论Id
    private Integer commentId;

    //评论内容
    private String content;

    //点赞数
    private Integer likes;

    //评论用户
    private Integer userId;
    //回复用户
    private Integer replyUserId;

    //置顶
    private Integer isTop;

    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    //逻辑删除
    @TableLogic(value = "0",delval = "1")
    private Integer deleted;


}

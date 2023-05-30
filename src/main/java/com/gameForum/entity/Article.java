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
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //标题
    private String title;
    //游戏id
    private Integer gameId;

    private Integer articleTypeId;

    private String cover;

    private String description;
    //是否置顶
    private Integer isTop;
    //帖子内容
    private String content;

    //浏览数
    private Integer view;

    //点赞数
    private Integer likes;

    //发帖用户
    private Integer userId;
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

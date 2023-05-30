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
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //名字
    private String name;

    //游戏类型
    private Integer type;

    //游戏平台
    private Integer platform;

    //游戏介绍
    private String description;
    //1审核通过 0未通过
    private Integer status;

    //创建用户
    private Integer createUser;

    //更新用户
    private Integer updateUser;

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

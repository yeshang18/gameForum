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
public class GameType implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //类型名称
    private String typeName;
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

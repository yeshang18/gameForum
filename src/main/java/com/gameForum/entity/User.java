package com.gameForum.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    private String img;

    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 积分
     */
    private Integer integral;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 0为普通账号，1为管理员
     */
    private Integer power;

    private LocalDateTime create_time;

    private LocalDateTime update_time;

    private Integer deleted;

    /**
     * 0正常，1封禁
     */
    private Integer status;


}

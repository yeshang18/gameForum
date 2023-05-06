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
public class View_record implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer article_id;

    private Integer user_id;

    private LocalDateTime create_time;

    private LocalDateTime update_time;

    private Integer deleted;


}

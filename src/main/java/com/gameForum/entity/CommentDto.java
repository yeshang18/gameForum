package com.gameForum.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;
@Data
public class CommentDto extends Comment{
    //是否点赞
    private Boolean flag;

    private List<CommentDto> children;

    private String userName;

    private String img;

    private String replyUserName;
}

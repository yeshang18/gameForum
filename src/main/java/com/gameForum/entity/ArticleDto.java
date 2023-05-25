package com.gameForum.entity;

import lombok.Data;

import javax.swing.text.StyledEditorKit;

@Data
public class ArticleDto extends Article{
    private Boolean flag;

    private String userName;

    private String gameName;

    private String typeName;

    private Integer comments;
}

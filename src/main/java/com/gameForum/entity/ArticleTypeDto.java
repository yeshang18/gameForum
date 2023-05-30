package com.gameForum.entity;

import lombok.Data;

@Data
public class ArticleTypeDto extends ArticleType{
    private String createUserName;

    private String updateUserName;

}

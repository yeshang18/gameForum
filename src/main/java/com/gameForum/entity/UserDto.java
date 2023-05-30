package com.gameForum.entity;

import lombok.Data;

@Data
public class UserDto extends User{

    private int totalLikes;

    private int countArticle;
}

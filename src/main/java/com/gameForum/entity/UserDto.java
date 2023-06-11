package com.gameForum.entity;

import lombok.Data;

@Data
public class UserDto extends User{

    private String code;

    private String verifyCode;

    private int totalLikes;

    private int countArticle;
}

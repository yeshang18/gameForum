package com.gameForum.entity;

import lombok.Data;

@Data
public class PlatformDto extends Platform{
    private String createUserName;

    private String updateUserName;
}

package com.gameForum.entity;

import lombok.Data;

@Data
public class ForumSettingDto extends ForumSetting{
    private String createUserName;

    private String updateUserName;
}

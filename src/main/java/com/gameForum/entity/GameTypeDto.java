package com.gameForum.entity;

import lombok.Data;

@Data
public class GameTypeDto extends GameType{

    private String createUserName;

    private String updateUserName;

}

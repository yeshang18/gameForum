package com.gameForum.entity;

import lombok.Data;

@Data
public class GameDto extends Game{
    private String gameTypeName;

    private String platformName;
}

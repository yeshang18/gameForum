package com.gameForum.utils;

import com.gameForum.entity.User;
import com.gameForum.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class GetInfo {

    @Autowired
    private UserService userService;



    public User getUser(Integer id){
        User user = userService.getById(id);
        return user;
    }
}

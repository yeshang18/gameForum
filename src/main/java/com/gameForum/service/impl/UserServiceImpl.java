package com.gameForum.service.impl;

import com.gameForum.entity.User;
import com.gameForum.entity.UserDto;
import com.gameForum.mapper.UserMapper;
import com.gameForum.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yeshang18
 * @since 2023-04-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDto getUserInfo(Integer userId) {
        return userMapper.selectUserInfo(userId);
    }
}

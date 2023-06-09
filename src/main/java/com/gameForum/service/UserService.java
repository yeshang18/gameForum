package com.gameForum.service;

import com.gameForum.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gameForum.entity.UserDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yeshang18
 * @since 2023-04-21
 */
public interface UserService extends IService<User> {
    UserDto getUserInfo (Integer userId);
}

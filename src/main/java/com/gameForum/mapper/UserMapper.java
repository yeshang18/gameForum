package com.gameForum.mapper;

import com.gameForum.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gameForum.entity.UserDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yeshang18
 * @since 2023-04-21
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    UserDto selectUserInfo(Integer userId);
}

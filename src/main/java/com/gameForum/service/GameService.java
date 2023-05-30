package com.gameForum.service;

import com.gameForum.entity.Game;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gameForum.entity.GameDto;
import com.gameForum.entity.PageInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yeshang18
 * @since 2023-04-21
 */
public interface GameService extends IService<Game> {
    List<GameDto> getAllToDto(Integer pageNo, Integer pageSize, Integer status);
}

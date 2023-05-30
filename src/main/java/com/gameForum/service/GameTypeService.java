package com.gameForum.service;

import com.gameForum.entity.GameType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gameForum.entity.GameTypeDto;
import com.gameForum.entity.LikeRecord;
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
public interface GameTypeService extends IService<GameType> {
    List<GameTypeDto> getAllToDto(Integer pageNo, Integer pageSize, Integer status);
}

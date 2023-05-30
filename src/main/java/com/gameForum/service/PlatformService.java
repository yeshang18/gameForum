package com.gameForum.service;

import com.gameForum.entity.PageInfo;
import com.gameForum.entity.Platform;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gameForum.entity.PlatformDto;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yeshang18
 * @since 2023-04-21
 */
public interface PlatformService extends IService<Platform> {
    List<PlatformDto> getAllToDto(Integer pageNo, Integer pageSize, Integer status);
}

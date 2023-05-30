package com.gameForum.service.impl;

import com.gameForum.entity.GameType;
import com.gameForum.entity.GameTypeDto;
import com.gameForum.entity.PageInfo;
import com.gameForum.mapper.GameTypeMapper;
import com.gameForum.service.GameTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yeshang18
 * @since 2023-04-21
 */
@Service
public class GameTypeServiceImpl extends ServiceImpl<GameTypeMapper, GameType> implements GameTypeService {

    @Autowired
    private GameTypeMapper gameTypeMapper;
    @Override
    public List<GameTypeDto> getAllToDto(Integer pageNo, Integer pageSize, Integer status) {
        return gameTypeMapper.selectAllToDto(pageNo,pageSize,status);
    }
}

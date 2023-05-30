package com.gameForum.service.impl;

import com.gameForum.entity.Game;
import com.gameForum.entity.GameDto;
import com.gameForum.entity.PageInfo;
import com.gameForum.mapper.GameMapper;
import com.gameForum.service.GameService;
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
public class GameServiceImpl extends ServiceImpl<GameMapper, Game> implements GameService {
    @Autowired
    private GameMapper gameMapper;
    @Override
    public List<GameDto> getAllToDto(Integer pageNo, Integer pageSize, Integer status) {
        return gameMapper.selectAllToDto(pageNo, pageSize, status);
    }
}

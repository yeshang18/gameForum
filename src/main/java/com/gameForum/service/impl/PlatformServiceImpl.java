package com.gameForum.service.impl;

import com.gameForum.entity.PageInfo;
import com.gameForum.entity.Platform;
import com.gameForum.entity.PlatformDto;
import com.gameForum.mapper.PlatformMapper;
import com.gameForum.service.PlatformService;
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
public class PlatformServiceImpl extends ServiceImpl<PlatformMapper, Platform> implements PlatformService {
    @Autowired
    private PlatformMapper platformMapper;
    @Override
    public List<PlatformDto> getAllToDto(Integer pageNo, Integer pageSize, Integer status) {
        return platformMapper.selectAllToDto(pageNo, pageSize, status);
    }
}

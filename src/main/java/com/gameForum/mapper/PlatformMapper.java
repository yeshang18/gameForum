package com.gameForum.mapper;

import com.gameForum.entity.GameDto;
import com.gameForum.entity.PageInfo;
import com.gameForum.entity.Platform;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gameForum.entity.PlatformDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yeshang18
 * @since 2023-04-21
 */
@Mapper
public interface PlatformMapper extends BaseMapper<Platform> {
    List<PlatformDto> selectAllToDto(Integer pageNo, Integer pageSize, Integer status);
}

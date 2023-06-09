package com.gameForum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gameForum.entity.ArticleTypeDto;
import com.gameForum.entity.ForumSetting;
import com.gameForum.entity.ForumSettingDto;
import com.gameForum.entity.Platform;
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
public interface ForumSettingMapper extends BaseMapper<ForumSetting> {
    List<ForumSettingDto> selectAllToDto(Integer pageNo, Integer pageSize);
}

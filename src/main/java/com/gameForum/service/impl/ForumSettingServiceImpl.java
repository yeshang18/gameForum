package com.gameForum.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gameForum.entity.ForumSetting;
import com.gameForum.entity.GameType;
import com.gameForum.mapper.ForumSettingMapper;
import com.gameForum.mapper.GameTypeMapper;
import com.gameForum.service.ForumSettingService;
import com.gameForum.service.GameTypeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yeshang18
 * @since 2023-04-21
 */
@Service
public class ForumSettingServiceImpl extends ServiceImpl<ForumSettingMapper, ForumSetting> implements ForumSettingService {

}

package com.gameForum.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gameForum.entity.Article;
import com.gameForum.entity.ArticleType;
import com.gameForum.entity.ArticleTypeDto;
import com.gameForum.mapper.ArticleMapper;
import com.gameForum.mapper.ArticleTypeMapper;
import com.gameForum.service.ArticleService;
import com.gameForum.service.ArticleTypeService;
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
public class ArticleTypeServiceImpl extends ServiceImpl<ArticleTypeMapper, ArticleType> implements ArticleTypeService {

    @Autowired
    private ArticleTypeMapper articleTypeMapper;
    @Override
    public List<ArticleTypeDto> getAllToDto(Integer pageNo, Integer pageSize) {
        return articleTypeMapper.selectAllToDto(pageNo, pageSize);
    }
}

package com.gameForum.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gameForum.entity.Article;
import com.gameForum.entity.ArticleType;
import com.gameForum.mapper.ArticleMapper;
import com.gameForum.mapper.ArticleTypeMapper;
import com.gameForum.service.ArticleService;
import com.gameForum.service.ArticleTypeService;
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
public class ArticleTypeServiceImpl extends ServiceImpl<ArticleTypeMapper, ArticleType> implements ArticleTypeService {

}

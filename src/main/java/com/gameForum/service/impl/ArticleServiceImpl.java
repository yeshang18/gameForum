package com.gameForum.service.impl;

import com.gameForum.entity.Article;
import com.gameForum.entity.ArticleDto;
import com.gameForum.mapper.ArticleMapper;
import com.gameForum.service.ArticleService;
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
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public List<ArticleDto> getByTitle(String title,Integer pageNo,Integer pageSize,Integer userId) {
        return articleMapper.selectArticleByTitle(title,pageNo,pageSize,userId);
    }

    @Override
    public List<ArticleDto> getByGame(Integer gameId,Integer pageNo,Integer pageSize,Integer userId) {
        return articleMapper.selectArticleByGame(gameId,pageNo,pageSize,userId);
    }

    @Override
    public ArticleDto getArticleById(Integer id,Integer userId) {
        return articleMapper.selectArticleById(id,userId);
    }
}

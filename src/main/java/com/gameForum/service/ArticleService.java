package com.gameForum.service;

import com.gameForum.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gameForum.entity.ArticleDto;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yeshang18
 * @since 2023-04-21
 */
public interface ArticleService extends IService<Article> {
    List<ArticleDto> getByTitle(String title,Integer pageNo,Integer pageSize,Integer userId);

    List<ArticleDto> getByGame(Integer gameId,Integer pageNo,Integer pageSize,Integer userId);

    ArticleDto getArticleById(Integer id,Integer userId);
}

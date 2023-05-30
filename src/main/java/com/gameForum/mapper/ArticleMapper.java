package com.gameForum.mapper;

import com.gameForum.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gameForum.entity.ArticleDto;
import com.google.common.collect.Interner;
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
public interface ArticleMapper extends BaseMapper<Article> {

    List<ArticleDto> selectArticleByTitle(String title, Integer pageNo,Integer pageSize,Integer userId);

    List<ArticleDto> selectArticleByGame(Integer gameId,Integer pageNo,Integer pageSize,Integer userId,Integer articleTypeId);

    ArticleDto selectArticleById(Integer id,Integer userId);

    List<ArticleDto> selectArticleByUser(Integer byUserId, Integer pageNo,Integer pageSize,Integer userId);
}

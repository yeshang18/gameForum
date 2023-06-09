package com.gameForum.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gameForum.entity.Article;
import com.gameForum.entity.ArticleType;
import com.gameForum.entity.ArticleTypeDto;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yeshang18
 * @since 2023-04-21
 */
public interface ArticleTypeService extends IService<ArticleType> {
    List<ArticleTypeDto> getAllToDto(Integer pageNo, Integer pageSize);
}

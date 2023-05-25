package com.gameForum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gameForum.entity.Article;
import com.gameForum.entity.ArticleType;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yeshang18
 * @since 2023-04-21
 */
@Mapper
public interface ArticleTypeMapper extends BaseMapper<ArticleType> {

}

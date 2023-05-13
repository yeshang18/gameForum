package com.gameForum.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gameForum.common.R;
import com.gameForum.entity.Article;
import com.gameForum.service.ArticleService;
import com.google.common.collect.Interner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yeshang18
 * @since 2023-04-21
 */
@RestController
@RequestMapping("/article")
@Api(tags = "帖子接口")
public class ArticleController {
    @Autowired
    private ArticleService articleService;


    @PostMapping("/save")
    @ApiOperation("发帖")
    public R<String> save(@RequestBody Article article){
        articleService.save(article);
        return R.success("发帖成功！");
    }


    @GetMapping("/all")
    @ApiOperation("获取全部帖子")
    public R<List<Article>> getAll(){
        List<Article> list = articleService.list();

        return R.success(list);

    }

    @GetMapping("/game")
    @ApiOperation("根据游戏获取帖子")
    public R<List<Article>> getByGame(Integer gameId){
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Article::getGameId,gameId);
        List<Article> list = articleService.list(lambdaQueryWrapper);

        return R.success(list);

    }

    @GetMapping("/byTitle")
    @ApiOperation("根据标题获取帖子")
    public R<List<Article>> getByTitle(String title){
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Article::getTitle,title);
        List<Article> list = articleService.list(lambdaQueryWrapper);

        return R.success(list);

    }


    @DeleteMapping("/delete")
    @ApiOperation("删帖")
    public R<String> deleted(@RequestBody Article article){
        articleService.removeById(article);
        return R.success("删除成功");
    }

}


package com.gameForum.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gameForum.common.R;
import com.gameForum.entity.*;
import com.gameForum.service.ArticleService;

import com.gameForum.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public R<PageInfo<ArticleDto>> getByGame(Integer gameId,
                                             @RequestParam(value="pageNo",required = false,defaultValue = "1") Integer pageNo,
                                             @RequestParam(value="pageSize",required = false,defaultValue = "10") Integer pageSize,
                                             HttpServletRequest request){
        Integer offset = (pageNo-1)*pageSize;
        String token =request.getHeader("Authorization").split(" ")[1];
        System.out.println(token);
        System.out.println(token==null);
        System.out.println(token=="null");
        //Integer userId = TokenUtil.getUserId(token);
        Integer userId=null;
        List<ArticleDto> list = articleService.getByGame(gameId,offset,pageSize,userId);
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Article::getGameId,gameId);
        int count = articleService.count(lambdaQueryWrapper);
        PageInfo<ArticleDto> pageInfo = new PageInfo<>();
        pageInfo.setTotal(count);
        pageInfo.setSize(pageSize);
        pageInfo.setCurrent(pageNo);
        pageInfo.setRecords(list);
        Integer pages =(int) Math.ceil((double) count/pageSize);
        pageInfo.setPages(pages);
        return R.success(pageInfo);
    }

    @GetMapping("/getById")
    @ApiOperation("根据id获取帖子")
    public R<ArticleDto> getById(Integer id, HttpServletRequest request){
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = TokenUtil.getUserId(token);
        ArticleDto articleDto = articleService.getArticleById(id,userId);
        if(articleDto == null){
            return R.error("该帖子被吃掉了喵~");
        }
        return R.success(articleDto);
    }

    @GetMapping("/byTitle")
    @ApiOperation("根据标题获取帖子")
    public R<PageInfo<ArticleDto>> getByTitle(String title,
                                              @RequestParam(value="pageNo",required = false,defaultValue = "1") Integer pageNo,
                                              @RequestParam(value="pageSize",required = false,defaultValue = "10") Integer pageSize,
                                              HttpServletRequest request){
        Integer offset = (pageNo-1)*pageSize;
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = TokenUtil.getUserId(token);
        List<ArticleDto> list = articleService.getByTitle(title,offset,pageSize,userId);
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Article::getTitle,title);
        int count = articleService.count(lambdaQueryWrapper);
        PageInfo<ArticleDto> pageInfo = new PageInfo<>();
        pageInfo.setTotal(count);
        pageInfo.setSize(pageSize);
        pageInfo.setCurrent(pageNo);
        pageInfo.setRecords(list);
        Integer pages =(int) Math.ceil((double) count/pageSize);
        pageInfo.setPages(pages);
        return R.success(pageInfo);
    }


    @DeleteMapping("/delete")
    @ApiOperation("删帖")
    public R<String> deleted(@RequestBody Article article){
        articleService.removeById(article);
        return R.success("删除成功");
    }

}


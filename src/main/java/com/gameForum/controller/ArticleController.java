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
    public R<ArticleDto> save(@RequestBody Article article,HttpServletRequest request){
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            userId = TokenUtil.getUserId(token);
            article.setUserId(userId);
        }
        else{
            return R.loginError("请登录!");
        }
        articleService.save(article);
        ArticleDto articleDto = articleService.getArticleById(article.getId(),userId);
        return R.success(articleDto);
    }


    @GetMapping("/all")
    @ApiOperation("获取全部帖子")
    public R<List<Article>> getAll(){
        List<Article> list = articleService.list();

        return R.success(list);

    }
    @GetMapping("/byUser")
    @ApiOperation("获取用户全部帖子")
    public R<PageInfo<ArticleDto>> getByUser(Integer byUserId,
                                             @RequestParam(value="pageNo",required = false,defaultValue = "1") Integer pageNo,
                                             @RequestParam(value="pageSize",required = false,defaultValue = "10") Integer pageSize,
                                             HttpServletRequest request){
        Integer offset = (pageNo-1)*pageSize;
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            userId = TokenUtil.getUserId(token);
        }
        List<ArticleDto> list = articleService.getByUser(byUserId,offset,pageSize,userId);
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Article::getUserId,byUserId);
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


    @GetMapping("/game")
    @ApiOperation("根据游戏获取帖子")
    public R<PageInfo<ArticleDto>> getByGame(Integer gameId,
                                             @RequestParam(value="pageNo",required = false,defaultValue = "1") Integer pageNo,
                                             @RequestParam(value="pageSize",required = false,defaultValue = "10") Integer pageSize,
                                             @RequestParam(value="articleTypeId",required = false,defaultValue = "0") Integer articleTypeId,
                                             HttpServletRequest request){
        Integer offset = (pageNo-1)*pageSize;
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            userId = TokenUtil.getUserId(token);
        }
        List<ArticleDto> list = articleService.getByGame(gameId,offset,pageSize,userId,articleTypeId);
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

    @GetMapping("/{articleId}")
    @ApiOperation("根据id获取帖子")
    public R<ArticleDto> getById(@PathVariable Integer articleId, HttpServletRequest request){
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            userId = TokenUtil.getUserId(token);
        }
        ArticleDto articleDto = articleService.getArticleById(articleId,userId);
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
        Integer userId = null;
        if(!token.equals("null")){
            userId = TokenUtil.getUserId(token);
        }
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

    @PutMapping("/setTop")
    @ApiOperation("置顶")
    public R<String> setTop(@RequestBody Article article){
        Article getArticle = articleService.getById(article.getId());
        if(getArticle==null){
            return R.nferror("该帖子不存在！");
        } else if (getArticle.getIsTop()!=0) {
            return R.error("操作异常");
        }
        articleService.updateById(article);
        return R.success("置顶成功！");
    }


    @PutMapping("/unTop")
    @ApiOperation("取消置顶")
    public R<String> unTop(@RequestBody Article article){
        Article getArticle = articleService.getById(article.getId());
        if(getArticle==null){
            return R.nferror("该帖子不存在！");
        } else if (getArticle.getIsTop()!=1) {
            return R.error("操作异常");
        }
        articleService.updateById(article);
        return R.success("取消置顶成功！");
    }

}


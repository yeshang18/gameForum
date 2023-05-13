package com.gameForum.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gameForum.common.R;
import com.gameForum.entity.Article;
import com.gameForum.entity.LikeRecord;
import com.gameForum.service.ArticleService;
import com.gameForum.service.LikeRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/likeRecord")
@Api(tags = "点赞记录接口")
public class LikeRecordController {

    @Autowired
    private LikeRecordService likeRecordService;

    @Autowired
    private ArticleService articleService;

    @PostMapping("/save")
    @ApiOperation("保存点赞记录")
    @Transactional
    public R<String> save(@RequestBody LikeRecord likeRecord){
        likeRecordService.save(likeRecord);
        Article article =  articleService.getById(likeRecord.getArticleId());
        article.setLike(article.getLike()+1);
        articleService.updateById(article);
        return R.success("操作成功！");
    }

    @PostMapping("/delete")
    @ApiOperation("取消点赞记录")
    @Transactional
    public R<String> deleted(@RequestBody LikeRecord likeRecord){
        LambdaQueryWrapper<LikeRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(LikeRecord::getArticleId,likeRecord.getArticleId());
        lambdaQueryWrapper.eq(LikeRecord::getUserId,likeRecord.getUserId());

        likeRecordService.remove(lambdaQueryWrapper);
        Article article =  articleService.getById(likeRecord.getArticleId());
        article.setLike(article.getLike()-1);
        articleService.updateById(article);

        return R.success("操作成功");
    }


    @GetMapping("/list")
    @ApiOperation("获取点赞记录")
    public R<List<LikeRecord>> list(Integer id){
        LambdaQueryWrapper<LikeRecord> lambdaQueryWrapper =new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(LikeRecord::getUserId,id);
        List<LikeRecord> list = likeRecordService.list(lambdaQueryWrapper);
        return R.success(list);
    }

}


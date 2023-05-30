package com.gameForum.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gameForum.common.R;
import com.gameForum.entity.Article;
import com.gameForum.entity.Comment;
import com.gameForum.entity.LikeRecord;
import com.gameForum.service.ArticleService;
import com.gameForum.service.CommentService;
import com.gameForum.service.LikeRecordService;
import com.gameForum.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

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

    @Autowired
    private CommentService commentService;

    @PostMapping("/save")
    @ApiOperation("保存点赞记录")
    @Transactional
    public R<String> save(@RequestBody LikeRecord likeRecord,HttpServletRequest request){
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            userId = TokenUtil.getUserId(token);
            likeRecord.setUserId(userId);
        }
        else{
            return R.loginError("请登录!");
        }
        LambdaQueryWrapper<LikeRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(likeRecord.getArticleId()!=null,LikeRecord::getArticleId,likeRecord.getArticleId());
        lambdaQueryWrapper.eq(likeRecord.getCommentId()!=null,LikeRecord::getCommentId,likeRecord.getCommentId());
        lambdaQueryWrapper.eq(LikeRecord::getUserId,likeRecord.getUserId());
        int count = likeRecordService.count(lambdaQueryWrapper);
        if(count!=0){
            return R.error("请勿重复点赞！");
        }
        likeRecordService.save(likeRecord);
        if(likeRecord.getArticleId()!=null) {
            Article article = articleService.getById(likeRecord.getArticleId());
            article.setLikes(article.getLikes() + 1);
            articleService.updateById(article);
        } else if (likeRecord.getCommentId()!=null) {
            Comment comment = commentService.getById(likeRecord.getCommentId());
            comment.setLikes(comment.getLikes() + 1);
            commentService.updateById(comment);
        }

        return R.success("操作成功！");
    }

    @PostMapping("/delete")
    @ApiOperation("取消点赞记录")
    @Transactional
    public R<String> deleted(@RequestBody LikeRecord likeRecord,HttpServletRequest request){
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            userId = TokenUtil.getUserId(token);
            likeRecord.setUserId(userId);
        }
        else{
            return R.loginError("请登录!");
        }
        LambdaQueryWrapper<LikeRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(likeRecord.getArticleId()!=null,LikeRecord::getArticleId,likeRecord.getArticleId());
        lambdaQueryWrapper.eq(likeRecord.getCommentId()!=null,LikeRecord::getCommentId,likeRecord.getCommentId());
        lambdaQueryWrapper.eq(LikeRecord::getUserId,likeRecord.getUserId());
        int count = likeRecordService.count(lambdaQueryWrapper);
        if(count==0){
            return R.error("操作异常!");
        }
        likeRecordService.remove(lambdaQueryWrapper);
        if(likeRecord.getArticleId()!=null) {
            Article article = articleService.getById(likeRecord.getArticleId());
            if(article.getLikes()<=0)
                return R.error("操作异常!");
            article.setLikes(article.getLikes() - 1);
            articleService.updateById(article);
        } else if (likeRecord.getCommentId()!=null) {
            Comment comment = commentService.getById(likeRecord.getCommentId());
            if(comment.getLikes() <=0)
                return R.error("操作异常!");
            comment.setLikes(comment.getLikes() - 1);
            commentService.updateById(comment);
        }
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


package com.gameForum.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gameForum.common.R;
import com.gameForum.entity.Article;
import com.gameForum.entity.ViewRecord;
import com.gameForum.service.ArticleService;
import com.gameForum.service.ViewRecordService;
import com.gameForum.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
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
@RequestMapping("/viewRecord")
@Api(tags = "浏览记录接口")
public class ViewRecordController {

    @Autowired
    private ViewRecordService viewRecordService;
    @Autowired
    private ArticleService articleService;

    @PostMapping("/save")
    @ApiOperation("保存记录")
    public R<String> saveRecord(@RequestBody ViewRecord viewRecord, HttpServletRequest request){
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            if(TokenUtil.checkSign(token)) {
                userId = TokenUtil.getUserId(token);
                viewRecord.setUserId(userId);
            }
            else {
                return R.error("登陆超时，请重新登录");
            }
        }
        else{
            return R.success("未登录，不予记录");
        }

        LambdaQueryWrapper<ViewRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ViewRecord::getUserId,userId);
        lambdaQueryWrapper.eq(ViewRecord::getArticleId,viewRecord.getArticleId());
        ViewRecord one = viewRecordService.getOne(lambdaQueryWrapper);
        if(one!=null){
            Boolean flag = LocalDateTime.now().minusHours(1).isAfter(one.getUpdateTime());
            if(flag){
                Article article = articleService.getById(viewRecord.getArticleId());
                article.setView(article.getView()+1);
                articleService.updateById(article);
                viewRecordService.save(viewRecord);
            }
           viewRecordService.updateById(one);
           return R.success("更新成功");
        }
        Article article = articleService.getById(viewRecord.getArticleId());
        article.setView(article.getView()+1);
        articleService.updateById(article);
        viewRecordService.save(viewRecord);
        return R.success("保存成功！");
    }

    @GetMapping("/list")
    @ApiOperation("获取浏览记录")
    public R<List<ViewRecord>> list(HttpServletRequest request){
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            if(TokenUtil.checkSign(token)) {
                userId = TokenUtil.getUserId(token);
            }
            else {
                return R.error("登陆超时，请重新登录");
            }
        }
        else{
            return R.error("未登录，请登录");
        }
        LambdaQueryWrapper<ViewRecord> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ViewRecord::getUserId,userId);
        List<ViewRecord> list =  viewRecordService.list(lambdaQueryWrapper);
        return R.success(list);

    }

}


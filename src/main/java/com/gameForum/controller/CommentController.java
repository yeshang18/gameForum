package com.gameForum.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gameForum.common.R;
import com.gameForum.entity.*;
import com.gameForum.service.ArticleService;
import com.gameForum.service.CommentService;
import com.gameForum.service.LikeRecordService;
import com.gameForum.service.UserService;
import com.gameForum.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yeshang18
 * @since 2023-04-21
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "评论接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;


    @PostMapping("/save")
    @ApiOperation("发表评论")
    public R<CommentDto> save(@RequestBody Comment comment,HttpServletRequest request){
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            userId = TokenUtil.getUserId(token);
            comment.setUserId(userId);
        }
        else{
            return R.loginError("请登录!");
        }
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getUserId,userId);
        lambdaQueryWrapper.ge(Comment::getCreateTime, LocalDateTime.now().minusSeconds(5));
        //List<Comment> list = commentService.list(lambdaQueryWrapper);
        //Comment list = commentService.getOne(lambdaQueryWrapper);
        int count = commentService.count(lambdaQueryWrapper);
        if (count>0)
            return R.error("发送评论过快喵！");

        if(comment.getArticleId()!=null)
        {
            LambdaQueryWrapper<Article> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Article::getId,comment.getArticleId());
            int countA = articleService.count(lqw);
            if (countA==0)
                return R.error("该帖子被吃掉了喵~");
        }
        else if(comment.getCommentId()!=null)
        {
            LambdaQueryWrapper<Comment> lqw = new LambdaQueryWrapper<>();
            lqw.eq(Comment::getId,comment.getCommentId());
            int countA = commentService.count(lqw);
            if (countA==0)
                return R.error("该评论已消失！");
        }
        CommentDto commentDto = new CommentDto();
        commentService.save(comment);
        if(comment.getArticleId()!=null) {
            commentDto = commentService.getCommentById(comment.getId(), userId);
        }
        else if (comment.getCommentId()!=null){
            List<CommentDto> list = commentService.getChildrenComments(comment.getCommentId(),userId);
            commentDto.setChildren(list);
        }
        return R.success(commentDto);
    }
//非树状评论
//    @GetMapping("/byArticle")
//    @ApiOperation("获取所有评论")
//    public R<List<CommentDto>> byMusic(Integer id, Integer userId, @RequestParam(value="order",required = false,defaultValue = "1")Integer order) {
//
//        //获取所有评论
//        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(Comment::getArticleId,id);
//        if (order==1)//按发布时间降序排列
//            lambdaQueryWrapper.orderByAsc(Comment::getCreateTime);
//        if (order==2)//按点赞数排列
//            lambdaQueryWrapper.orderByDesc(Comment::getLikes, Comment::getCreateTime);
//        List<Comment> comments = commentService.list(lambdaQueryWrapper);
//        //获取用户点赞信息
//        LambdaQueryWrapper<LikeRecord> lqw = new LambdaQueryWrapper<>();
//        lqw.eq(LikeRecord::getUserId, userId);
//        List<LikeRecord> likes = likeRecordService.list(lqw);
//
//        //获取用户信息
//        Map<Integer, User> mapU =new HashMap<>();
//        List<User> users = userService.list();
//        for (User u:users){
//            mapU.put(u.getId(),u);
//        }
//
//        //将用户点赞信息存入Map
//        Map<Integer,LikeRecord> map =new HashMap<>();
//        for(LikeRecord a:likes){
//            map.put(a.getCommentId(),a);
//        }
//        //将评论存入Map
//        Map<Integer,Comment> mapAll =new HashMap<>();
//        for(Comment c:comments){
//            mapAll.put(c.getId(),c);
//        }
//
//        //将comment转换为commentDto
//        List<CommentDto> commentDtos = comments.stream().map((item)->{
//            CommentDto commentDto =new CommentDto();
//            BeanUtils.copyProperties(item,commentDto);
//            User user = mapU.get(item.getUserId());
//            commentDto.setUsrName(user.getName());
//            commentDto.setImg(user.getImg());
//            if(map.containsKey(item.getId()))
//                commentDto.setFlag(true);
//            if(item.getArticleId()!=null) {
//                Comment c = mapAll.get(item.getArticleId());
//                commentDto.setReplyUserName(mapU.get(c.getUserId()).getName());
//                commentDto.setReplyContent(c.getContent());
//            }
//            return commentDto;
//        }).toList();
//
//        return R.success(commentDtos);
//    }

    //树状评论
    @GetMapping("/byArticle")
    @ApiOperation("获取所有评论")
    public R<PageInfo<CommentDto>> byArticle(Integer articleId,
                                           @RequestParam(value="pageNo",required = false,defaultValue = "1") Integer pageNo,
                                           @RequestParam(value="pageSize",required = false,defaultValue = "10") Integer pageSize,
                                           @RequestParam(value="order",required = false,defaultValue = "1")Integer order,
                                           HttpServletRequest request) {

        pageNo = (pageNo-1)*pageSize;
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            userId = TokenUtil.getUserId(token);
        }
        List<CommentDto> list = new ArrayList<>();
        //获取所有评论
        if (order==2) {//时间排序
            list = commentService.getCommentOrderByTime(articleId, pageNo, pageSize, userId);
        }
        else if(order == 1){//点赞排序
            list = commentService.getCommentOrderByLikes(articleId, pageNo, pageSize, userId);
        }
        for ( CommentDto c:list){
            List<CommentDto> list1 = commentService.getChildrenComments(c.getId(),userId);
            c.setChildren(list1);
        }
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getArticleId,articleId);
        int count = commentService.count(lambdaQueryWrapper);
        Integer pages = (int)Math.ceil((double) count/pageSize);
        PageInfo<CommentDto> pageInfo = new PageInfo<>();
        pageInfo.setPages(pages);
        pageInfo.setSize(pageSize);
        pageInfo.setCurrent(pageNo);
        pageInfo.setRecords(list);
        pageInfo.setTotal(count);
        return R.success(pageInfo);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除评论")
    @Transactional
    public R<String> deleteById(Integer id)
    {
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getCommentId,id);
        commentService.remove(lambdaQueryWrapper);
        commentService.removeById(id);
        return R.success("删除成功");
    }

    @GetMapping("/byId")
    @ApiOperation("根据id查询")
    public R<Comment> byId(Integer id){
        Comment comment = commentService.getById(id);
        return R.success(comment);
    }

    @PutMapping("/setTop")
    @ApiOperation("置顶")
    public R<String> setTop(@RequestBody Comment comment){
        Comment getC = commentService.getById(comment.getId());
        if(getC==null){
            return R.nferror("该评论不存在!");
        }
        else if(getC.getIsTop()!=0){
            return R.error("操作异常!");
        }

        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getIsTop,1);
        Comment upC = new Comment();
        upC.setIsTop(0);
        commentService.update(upC,lambdaQueryWrapper);
        commentService.updateById(comment);
        return R.success("更新成功");
    }

    @PutMapping("/unTop")
    @ApiOperation("取消置顶")
    public R<String> unTop(@RequestBody Comment comment){
        Comment getC = commentService.getById(comment.getId());
        if(getC==null){
            return R.nferror("该评论不存在!");
        }
        else if(getC.getIsTop()!=1){
            return R.error("操作异常!");
        }

        commentService.updateById(comment);
        return R.success("更新成功");
    }

}


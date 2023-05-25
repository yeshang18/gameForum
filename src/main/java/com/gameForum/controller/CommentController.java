package com.gameForum.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gameForum.common.R;
import com.gameForum.entity.*;
import com.gameForum.service.CommentService;
import com.gameForum.service.LikeRecordService;
import com.gameForum.service.UserService;
import com.gameForum.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    private LikeRecordService likeRecordService;

    @Autowired
    private UserService userService;


    @PostMapping("/publish")
    @ApiOperation("发表评论")
    public R<String> publish(@RequestBody Comment comment){

        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.ge(Comment::getCreateTime, LocalDateTime.now().minusSeconds(5));
        //List<Comment> list = commentService.list(lambdaQueryWrapper);
        Comment list = commentService.getOne(lambdaQueryWrapper);
        if (list!=null)
            return R.error("发送评论过快喵！");

        if(comment.getArticleId()!=null)
        {
            Comment comment1= commentService.getById(comment.getArticleId());
            if (comment1==null)
                return R.error("该帖子被吃掉了喵~");
        }

        commentService.save(comment);
        return R.success("保存成功!");
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
    public R<PageInfo<CommentDto>> byMusic(Integer articleId,
                                           @RequestParam(value="pageNo",required = false,defaultValue = "1") Integer pageNo,
                                           @RequestParam(value="pageSize",required = false,defaultValue = "10") Integer pageSize,
                                           @RequestParam(value="order",required = false,defaultValue = "1")Integer order,
                                           HttpServletRequest request) {

        pageNo = (pageNo-1)*pageSize;
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = TokenUtil.getUserId(token);
        List<CommentDto> list = new ArrayList<>();
        //获取所有评论
        if (order==1) {//时间排序
            list = commentService.getCommentOrderByTime(articleId, pageNo, pageSize, userId);
        }
        else if(order == 2){//点赞排序
            list = commentService.getCommentOrderByLikes(articleId, pageNo, pageSize, userId);
        }
        for ( CommentDto c:list){
            List<CommentDto> list1 = commentService.getChildrenComments(c.getId(), pageNo, pageSize, userId);
            c.setList(list1);
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

    @GetMapping("/delete")
    @ApiOperation("删除评论")
    public R<String> deleteById(Integer id)
    {
        commentService.removeById(id);
        return R.success("删除成功");
    }

    @GetMapping("/byId")
    @ApiOperation("根据id查询")
    public R<Comment> byId(Integer id){
        Comment comment = commentService.getById(id);
        return R.success(comment);
    }
}


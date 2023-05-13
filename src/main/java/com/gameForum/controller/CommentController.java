package com.gameForum.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gameForum.common.R;
import com.gameForum.entity.Comment;
import com.gameForum.entity.CommentDto;
import com.gameForum.entity.LikeRecord;
import com.gameForum.entity.User;
import com.gameForum.service.CommentService;
import com.gameForum.service.LikeRecordService;
import com.gameForum.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @GetMapping("/byArticle")
    @ApiOperation("获取所有评论")
    public R<List<CommentDto>> byMusic(Integer id, Integer userId, Integer order) {

        //获取所有评论
        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Comment::getArticleId,id);
        if (order==1)//按发布时间降序排列
            lambdaQueryWrapper.orderByDesc(Comment::getCreateTime);
        if (order==2)//按点赞数排列
            lambdaQueryWrapper.orderByDesc(Comment::getLike,Comment::getCreateTime);
        List<Comment> comments = commentService.list(lambdaQueryWrapper);
        //获取用户点赞信息
        LambdaQueryWrapper<LikeRecord> lqw = new LambdaQueryWrapper<>();
        lqw.eq(LikeRecord::getUserId, userId);
        List<LikeRecord> likes = likeRecordService.list(lqw);

        //获取用户信息
        Map<Integer, User> mapU =new HashMap<>();
        List<User> users = userService.list();
        for (User u:users){
            mapU.put(u.getId(),u);
        }

        //将用户点赞信息存入Map
        Map<Integer,LikeRecord> map =new HashMap<>();
        for(LikeRecord a:likes){
            map.put(a.getCommentId(),a);
        }
        //将评论存入Map
        Map<Integer,Comment> mapAll =new HashMap<>();
        for(Comment c:comments){
            mapAll.put(c.getId(),c);
        }

        //将comment转换为commentDto
        List<CommentDto> commentDtos = comments.stream().map((item)->{
            CommentDto commentDto =new CommentDto();
            BeanUtils.copyProperties(item,commentDto);
            User user = mapU.get(item.getUserId());
            commentDto.setUsrName(user.getName());
            commentDto.setImg(user.getImg());
            if(map.containsKey(item.getId()))
                commentDto.setFlag(true);
            if(item.getArticleId()!=null) {
                Comment c = mapAll.get(item.getArticleId());
                commentDto.setReplyUserName(mapU.get(c.getUserId()).getName());
                commentDto.setReplyContent(c.getContent());
            }
            return commentDto;
        }).toList();

        return R.success(commentDtos);
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


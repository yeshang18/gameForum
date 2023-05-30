package com.gameForum.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gameForum.common.R;
import com.gameForum.entity.Article;
import com.gameForum.entity.ArticleType;
import com.gameForum.service.ArticleService;
import com.gameForum.service.ArticleTypeService;
import com.gameForum.utils.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/articleType")
@Api(tags = "帖子类型接口")
public class ArticleTypeController {
    @Autowired
    private ArticleTypeService articleTypeService;


    @PostMapping("/add")
    @ApiOperation("添加类型")
    public R<String> save(@RequestBody ArticleType articleType, HttpServletRequest request){
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            if(TokenUtil.checkSign(token)) {
                userId = TokenUtil.getUserId(token);
                articleType.setCreateUser(userId);
                articleType.setUpdateUser(userId);
            }
            else{
                return R.loginError("请登录!");
            }
        }
        else{
            return R.loginError("请登录!");
        }
        articleTypeService.save(articleType);
        return R.success("添加成功！");
    }


    @GetMapping("/getAll")
    @ApiOperation("获取全部类型")
    public R<Page<ArticleType>> getAll(@RequestParam(value="pageNo",required = false,defaultValue = "1") Integer pageNo,
                                       @RequestParam(value="pageSize",required = false,defaultValue = "10") Integer pageSize){
        Page<ArticleType> pageinfo = new Page<>(pageNo,pageSize);
        articleTypeService.page(pageinfo);
        return R.success(pageinfo);
    }

    @PutMapping("/update")
    @ApiOperation("更新类型")
    public R<String> update(@RequestBody ArticleType articleType,HttpServletRequest request){
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            if(TokenUtil.checkSign(token)) {
                userId = TokenUtil.getUserId(token);
                articleType.setUpdateUser(userId);
            }
            else{
                return R.loginError("请登录!");
            }
        }
        else{
            return R.loginError("请登录!");
        }
        articleTypeService.updateById(articleType);
        return R.success("更新成功!");
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除类型")
    public R<String> deleted(@RequestBody ArticleType articleType){
        articleTypeService.removeById(articleType.getId());
        return R.success("删除成功");
    }

}


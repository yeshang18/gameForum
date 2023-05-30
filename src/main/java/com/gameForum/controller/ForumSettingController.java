package com.gameForum.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gameForum.common.R;
import com.gameForum.entity.ArticleType;
import com.gameForum.entity.ForumSetting;
import com.gameForum.service.ArticleTypeService;
import com.gameForum.service.ForumSettingService;
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
@RequestMapping("/forumSetting")
@Api(tags = "论坛设置接口")
public class ForumSettingController {
    @Autowired
    private ForumSettingService forumSettingService;


    @PostMapping("/add")
    @ApiOperation("添加设置")
    public R<String> save(@RequestBody ForumSetting forumSetting, HttpServletRequest request){
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            if(TokenUtil.checkSign(token)) {
                userId = TokenUtil.getUserId(token);
                forumSetting.setCreateUser(userId);
                forumSetting.setUpdateUser(userId);
            }
            else{
                return R.loginError("请登录!");
            }
        }
        else{
            return R.loginError("请登录!");
        }
        forumSettingService.save(forumSetting);
        return R.success("添加成功！");
    }


    @GetMapping("/getAll")
    @ApiOperation("获取全部设置")
    public R<Page<ForumSetting>> getAll(@RequestParam(value="pageNo",required = false,defaultValue = "1") Integer pageNo,
                                        @RequestParam(value="pageSize",required = false,defaultValue = "10") Integer pageSize){
        Page<ForumSetting> pageInfo  = new Page<>(pageNo,pageSize);
        forumSettingService.page(pageInfo);
        return R.success(pageInfo);
    }

    @PutMapping("/update")
    @ApiOperation("更新设置")
    public R<String> update(@RequestBody ForumSetting forumSetting,HttpServletRequest request){
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            if(TokenUtil.checkSign(token)) {
                userId = TokenUtil.getUserId(token);
                forumSetting.setUpdateUser(userId);
            }
            else{
                return R.loginError("请登录!");
            }
        }
        else{
            return R.loginError("请登录!");
        }
        forumSettingService.updateById(forumSetting);
        return R.success("更新成功!");
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除设置")
    public R<String> deleted(@RequestBody ForumSetting forumSetting){
        forumSettingService.removeById(forumSetting.getId());
        return R.success("删除成功");
    }

}


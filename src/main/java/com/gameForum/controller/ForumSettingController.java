package com.gameForum.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gameForum.common.R;
import com.gameForum.entity.*;
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
    public R<PageInfo<ForumSettingDto>> getAll(@RequestParam(value="pageNo",required = false,defaultValue = "1") Integer pageNo,
                                        @RequestParam(value="pageSize",required = false,defaultValue = "10") Integer pageSize){
        pageNo = (pageNo-1)*pageSize;
        List<ForumSettingDto> list = forumSettingService.getAllToDto(pageNo, pageSize);

        int count = forumSettingService.count();
        PageInfo<ForumSettingDto> pageInfo = new PageInfo<>();
        pageInfo.setTotal(count);
        pageInfo.setSize(pageSize);
        pageInfo.setCurrent(pageNo);
        pageInfo.setRecords(list);
        Integer pages =(int) Math.ceil((double) count/pageSize);
        pageInfo.setPages(pages);
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


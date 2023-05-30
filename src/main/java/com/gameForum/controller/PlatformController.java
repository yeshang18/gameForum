package com.gameForum.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gameForum.common.R;
import com.gameForum.entity.GameTypeDto;
import com.gameForum.entity.PageInfo;
import com.gameForum.entity.Platform;
import com.gameForum.entity.PlatformDto;
import com.gameForum.service.PlatformService;
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
@RequestMapping("/platform")
@Api(tags = "游戏平台接口")
public class PlatformController {
    @Autowired
    private PlatformService platformService;

    @GetMapping("/getAll")
    @ApiOperation("获取所有平台")
    public R<PageInfo<PlatformDto>> getAll(@RequestParam(value="pageNo",required = false,defaultValue = "1") Integer pageNo,
                                           @RequestParam(value="pageSize",required = false,defaultValue = "10") Integer pageSize,
                                           @RequestParam(value="status",required = false,defaultValue = "1") Integer status){
        pageNo = (pageNo - 1) * pageSize;

        List<PlatformDto> list = platformService.getAllToDto(pageNo, pageSize, status);

        LambdaQueryWrapper<Platform> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Platform::getStatus,status);
        int count = platformService.count(lambdaQueryWrapper);
        PageInfo<PlatformDto> pageInfo = new PageInfo<>();
        pageInfo.setTotal(count);
        pageInfo.setSize(pageSize);
        pageInfo.setCurrent(pageNo);
        pageInfo.setRecords(list);
        Integer pages = (int) Math.ceil((double) count / pageSize);
        pageInfo.setPages(pages);

        return R.success(pageInfo);
    }

    @PostMapping("/add")
    @ApiOperation("添加平台")
    public R<String> add(@RequestBody Platform platform, HttpServletRequest request){
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            if(TokenUtil.checkSign(token)) {
                userId = TokenUtil.getUserId(token);
                platform.setCreateUser(userId);
                platform.setUpdateUser(userId);
            }
            else{
                return R.loginError("请登录!");
            }
        }
        else{
            return R.loginError("请登录!");
        }
        platformService.save(platform);
        return R.success("添加成功！");
    }

    @PutMapping("/update")
    @ApiOperation("更新平台信息")
    public R<String> update(@RequestBody Platform platform,HttpServletRequest request){
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            if(TokenUtil.checkSign(token)) {
                userId = TokenUtil.getUserId(token);
                platform.setUpdateUser(userId);
            }
            else{
                return R.loginError("请登录!");
            }
        }
        else{
            return R.loginError("请登录!");
        }
        platformService.updateById(platform);
        return R.success(("更新成功!"));
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除平台信息")
    public R<String> delete(Integer id){
        platformService.removeById(id);
        return R.success("删除成功！");
    }
}


package com.gameForum.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gameForum.common.R;
import com.gameForum.entity.*;
import com.gameForum.service.GameTypeService;
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
@RequestMapping("/gameType")
@Api(tags = "游戏类型接口")
public class GameTypeController {
    @Autowired
    private GameTypeService gameTypeService;

    @GetMapping("/getAll")
    @ApiOperation("获取所有游戏类型")
    public R<PageInfo<GameTypeDto>> getAll(@RequestParam(value="pageNo",required = false,defaultValue = "1") Integer pageNo,
                                           @RequestParam(value="pageSize",required = false,defaultValue = "10") Integer pageSize,
                                           @RequestParam(value="status",required = false,defaultValue = "1") Integer status) {
        pageNo = (pageNo - 1) * pageSize;

        List<GameTypeDto> list = gameTypeService.getAllToDto(pageNo, pageSize, status);
        LambdaQueryWrapper<GameType> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(GameType::getStatus, status);
        int count = gameTypeService.count(lambdaQueryWrapper);
        PageInfo<GameTypeDto> pageInfo = new PageInfo<>();
        pageInfo.setTotal(count);
        pageInfo.setSize(pageSize);
        pageInfo.setCurrent(pageNo);
        pageInfo.setRecords(list);
        Integer pages = (int) Math.ceil((double) count / pageSize);
        pageInfo.setPages(pages);

        return R.success(pageInfo);
    }

    @PostMapping("/add")
    @ApiOperation("添加游戏类型")
    public R<String> add(@RequestBody GameType gameType, HttpServletRequest request){
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            if(TokenUtil.checkSign(token)) {
                userId = TokenUtil.getUserId(token);
                gameType.setCreateUser(userId);
                gameType.setUpdateUser(userId);
            }
            else{
                return R.loginError("请登录!");
            }
        }
        else{
            return R.loginError("请登录!");
        }

        gameTypeService.save(gameType);
        return R.success("添加成功！");
    }

    @PutMapping("/update")
    @ApiOperation("更新游戏类型信息")
    public R<String> update(@RequestBody GameType gameType,HttpServletRequest request) {
        String token =request.getHeader("Authorization").split(" ")[1];
        Integer userId = null;
        if(!token.equals("null")){
            if(TokenUtil.checkSign(token)) {
                userId = TokenUtil.getUserId(token);
                gameType.setUpdateUser(userId);
            }
            else{
                return R.loginError("请登录!");
            }
        }
        else{
            return R.loginError("请登录!");
        }
        gameTypeService.updateById(gameType);
        return R.success(("更新成功!"));
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除游戏类型")
    public R<String> delete(Integer id){
        gameTypeService.removeById(id);
        return R.success("删除成功!");
    }
}


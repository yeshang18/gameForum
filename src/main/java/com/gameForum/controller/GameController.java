package com.gameForum.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gameForum.common.R;
import com.gameForum.entity.Game;
import com.gameForum.service.GameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.ClientInfoStatus;
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
@RequestMapping("/game")
@Api("游戏信息接口")
public class GameController {

    @Autowired
    private GameService gameService;


    @ApiOperation("添加游戏")
    @PostMapping("/add")
    public R<String> add(@RequestBody Game game){
        gameService.save(game);
        return R.success("添加成功！");
    }

    @GetMapping("/getAll")
    @ApiOperation("获取所有游戏")
    public R<List<Game>> getALl(){
        List<Game> list = gameService.list();
        return R.success(list);
    }

    @GetMapping("/getById")
    @ApiOperation("根据id获取接口")
    public R<Game> getById(Integer id){
        Game game = gameService.getById(id);
        return R.success(game);
    }

    @GetMapping("/getByType")
    @ApiOperation("根据类型获取游戏")
    public R<List<Game>> getByType(Integer typeId){
        LambdaQueryWrapper<Game> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Game::getType,typeId);
        List<Game> list = gameService.list(lambdaQueryWrapper);
        return R.success(list);
    }

    @GetMapping("/getByPf")
    @ApiOperation("根据平台获取游戏")
    public R<List<Game>> getByPlatform(Integer platformId){
        LambdaQueryWrapper<Game> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Game::getPlatform,platformId);
        List<Game> list = gameService.list(lambdaQueryWrapper);
        return R.success(list);
    }

    @GetMapping("/getByName")
    @ApiOperation("根据名称获取游戏")
    public R<List<Game>> getByName(String name) {
        LambdaQueryWrapper<Game> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Game::getName,name);
        List<Game> list = gameService.list(lambdaQueryWrapper);
        return R.success(list);
    }

    @PutMapping("/update")
    @ApiOperation("更新游戏信息")
    public R<String> update(@RequestBody Game game){
        gameService.updateById(game);
        return R.success("更新成功!");
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除游戏信息")
    public R<String> delete(Integer id){
        gameService.removeById(id);
        return R.success("删除成功！");
    }

}


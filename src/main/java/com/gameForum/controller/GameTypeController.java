package com.gameForum.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gameForum.common.R;
import com.gameForum.entity.GameType;
import com.gameForum.entity.Platform;
import com.gameForum.service.GameTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public R<Page<GameType>> getAll(@RequestParam(value="pageNo",required = false,defaultValue = "1") Integer pageNo,
                                    @RequestParam(value="pageSize",required = false,defaultValue = "10") Integer pageSize,
                                    @RequestParam(value="status",required = false,defaultValue = "1") Integer status){
        Page<GameType> pageInfo = new Page<>(pageNo,pageSize);
        gameTypeService.page(pageInfo);
        return R.success(pageInfo);
    }

    @PostMapping("/add")
    @ApiOperation("添加游戏类型")
    public R<String> add(@RequestBody GameType gameType){
        gameTypeService.save(gameType);
        return R.success("添加成功！");
    }

    @PutMapping("/update")
    @ApiOperation("更新游戏类型信息")
    public R<String> update(@RequestBody GameType gameType) {
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


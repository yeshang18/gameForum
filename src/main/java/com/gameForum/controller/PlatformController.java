package com.gameForum.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gameForum.common.R;
import com.gameForum.entity.Platform;
import com.gameForum.service.PlatformService;
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
@RequestMapping("/platform")
@Api(tags = "游戏平台接口")
public class PlatformController {
    @Autowired
    private PlatformService platformService;

    @GetMapping("/getAll")
    @ApiOperation("获取所有平台")
    public R<Page<Platform>> getAll(@RequestParam(value="pageNo",required = false,defaultValue = "1") Integer pageNo,
                                    @RequestParam(value="pageSize",required = false,defaultValue = "10") Integer pageSize){
        Page<Platform> pageInfo = new Page<>(pageNo,pageSize);
        platformService.page(pageInfo);
        return R.success(pageInfo);
    }

    @PostMapping("/add")
    @ApiOperation("添加平台")
    public R<String> add(@RequestBody Platform platform){
        platformService.save(platform);
        return R.success("添加成功！");
    }

    @PutMapping("/update")
    @ApiOperation("更新平台信息")
    public R<String> update(@RequestBody Platform platform){
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


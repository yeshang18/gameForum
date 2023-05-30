package com.gameForum.controller;


import com.gameForum.common.R;
import com.gameForum.entity.ArticleType;
import com.gameForum.entity.ForumSetting;
import com.gameForum.service.ArticleTypeService;
import com.gameForum.service.ForumSettingService;
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
@RequestMapping("/forumSetting")
@Api(tags = "帖子类型接口")
public class ForumSettingController {
    @Autowired
    private ForumSettingService forumSettingService;


    @PostMapping("/add")
    @ApiOperation("添加类型")
    public R<String> save(@RequestBody ForumSetting forumSetting){
        forumSettingService.save(forumSetting);
        return R.success("添加成功！");
    }


    @GetMapping("/getAll")
    @ApiOperation("获取全部类型")
    public R<List<ForumSetting>> getAll(){
        List<ForumSetting> list = forumSettingService.list();
        return R.success(list);
    }

    @PutMapping("/update")
    @ApiOperation("更新类型")
    public R<String> update(@RequestBody ForumSetting forumSetting){
        forumSettingService.updateById(forumSetting);
        return R.success("更新成功!");
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除类型")
    public R<String> deleted(@RequestBody ForumSetting forumSetting){
        forumSettingService.removeById(forumSetting.getId());
        return R.success("删除成功");
    }

}


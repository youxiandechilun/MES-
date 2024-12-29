package com.example.controller;

import com.example.common.Result;
import com.example.entity.Material; // 注意这里的包路径应与 Material 实体类一致
import com.example.service.MaterialService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/material")
public class MaterialController {

    @Resource
    private MaterialService materialService;

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             Material material) {
        PageInfo<Material> pageInfo = materialService.selectPage(pageNum, pageSize, material); // 修改变量名为 pageInfo
        return Result.success(pageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Material material) {
        materialService.add(material);
        return Result.success(material);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Material material) {
        materialService.updateById(material);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        materialService.deleteById(id);
        return Result.success();
    }
}
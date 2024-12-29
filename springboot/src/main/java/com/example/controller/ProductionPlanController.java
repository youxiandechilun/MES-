package com.example.controller;

import com.example.common.Result;
import com.example.entity.ProductionPlan;
import com.example.service.ProductionPlanService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/productionplan")
public class ProductionPlanController {

    @Resource
    private ProductionPlanService productionPlanService;

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             ProductionPlan productionPlan) {
        PageInfo<ProductionPlan> productionPlanPageInfo = productionPlanService.selectPage(pageNum, pageSize, productionPlan);
        return Result.success(productionPlanPageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody ProductionPlan productionPlan) {
        productionPlanService.add(productionPlan);
        return Result.success(productionPlan);
    }

    @PutMapping("/update")
    public Result update(@RequestBody ProductionPlan productionPlan) {
        productionPlanService.updateById(productionPlan);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        productionPlanService.deleteById(id);
        return Result.success();
    }
}
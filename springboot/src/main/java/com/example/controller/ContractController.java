package com.example.controller;


import com.example.common.Result;
import com.example.entity.Contract;
import com.example.service.ContractService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/contract")
public class ContractController {
    @Resource
    private ContractService contractService;

    @GetMapping("/selectPage")
    public Result selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             Contract contract){
        PageInfo<Contract> contractPageInfo = contractService.selectPage(pageNum, pageSize,contract);
        return Result.success(contractPageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Contract contract){
        contractService.add(contract);
        return Result.success(contract);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Contract contract){
        contractService.updateById(contract);
        return Result.success();
    }

    @PutMapping("/update2")
    public Result update2(@RequestBody Contract contract){
        contractService.updateById2(contract);
        return Result.success();
    }

    @PutMapping("/update3")
    public Result update3(@RequestBody Contract contract){
        contractService.updateById2(contract);
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        contractService.deleteById(id);
        return Result.success();
    }

}

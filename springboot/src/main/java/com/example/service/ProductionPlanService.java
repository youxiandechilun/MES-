package com.example.service;

import com.example.entity.ProductionPlan;
import com.example.mapper.ProductionPlanMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ProductionPlanService {
    @Resource
    private ProductionPlanMapper productionPlanMapper;

    public PageInfo<ProductionPlan> selectPage(Integer pageNum, Integer pageSize, ProductionPlan productionPlan) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductionPlan> productionPlanList = productionPlanMapper.selectAll(productionPlan);
        return new PageInfo<>(productionPlanList);
    }

    @Transactional
    public void add(ProductionPlan productionPlan) {
        productionPlanMapper.insert(productionPlan);
    }

    @Transactional
    public void updateById(ProductionPlan productionPlan) {
        productionPlanMapper.updateById(productionPlan);
    }

    @Transactional
    public void deleteById(Integer id) {
        productionPlanMapper.deleteById(id);
    }
}
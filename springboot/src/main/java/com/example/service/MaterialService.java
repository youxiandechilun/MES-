package com.example.service;

import com.example.entity.Material;
import com.example.mapper.MaterialMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MaterialService {

    @Resource
    private MaterialMapper materialMapper;

    public PageInfo<Material> selectPage(Integer pageNum, Integer pageSize, Material material) {
        PageHelper.startPage(pageNum, pageSize);
        List<Material> materialList = materialMapper.selectAll(material);
        return new PageInfo<>(materialList);
    }

    @Transactional
    public void add(Material material) {
        materialMapper.insert(material);
    }

    @Transactional
    public void updateById(Material material) {
        materialMapper.updateById(material);
    }

    @Transactional
    public void deleteById(Integer id) {
        materialMapper.deleteById(id);
    }
}
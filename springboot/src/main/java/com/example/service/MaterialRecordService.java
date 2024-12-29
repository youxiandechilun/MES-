package com.example.service;

import com.example.entity.Contract;
import com.example.entity.MaterialRecords;
import com.example.mapper.ContractMapper;
import com.example.mapper.MaterialRecordsMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MaterialRecordService {

    @Resource
    private MaterialRecordsMapper materialRecordsMapper;


    public PageInfo<MaterialRecords> selectPage(Integer pageNum, Integer pageSize, MaterialRecords materialRecords){

        PageHelper.startPage(pageNum,pageSize);
        List<MaterialRecords> MaterialRecordsList = materialRecordsMapper.selectAll(materialRecords);
        return PageInfo.of(MaterialRecordsList);
    }


    @Transactional
    public void add(MaterialRecords materialRecords) {

        materialRecordsMapper.insert(materialRecords);

    }

}

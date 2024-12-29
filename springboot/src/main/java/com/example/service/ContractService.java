package com.example.service;
import com.example.entity.Contract;
import com.example.mapper.ContractMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ContractService {

    @Resource
    private ContractMapper contractMapper;


    public PageInfo<Contract> selectPage(Integer pageNum, Integer pageSize,Contract course){
        PageHelper.startPage(pageNum,pageSize);
        List<Contract> contractList = contractMapper.selectAll(course);
        return PageInfo.of(contractList);
    }



    @Transactional
    public void add(Contract contract) {
        // 插入合同
        contractMapper.insert(contract);

        // 插入客户信息
        contractMapper.insertCustomer(
                contract.getContractNumber(),
                contract.getContractName(),
                contract.getCustomerCode(),
                contract.getCustomerName(),
                contract.getCustomerPhone()
        );
    }

    @Transactional
    public void updateById(Contract contract) {
        // 更新合同
        contractMapper.updateById(contract);

        // 更新客户信息
        contractMapper.updateCustomer(
                contract.getContractNumber(),
                contract.getContractName(),
                contract.getCustomerCode(),
                contract.getCustomerName(),
                contract.getCustomerPhone(),
                contract.getId()
        );
    }

    @Transactional
    public void updateById2(Contract contract) {
        // 更新合同
        contractMapper.updateById2(contract);

    }

    @Transactional
    public void updateById3(Contract contract) {
        // 更新合同
        contractMapper.updateById3(contract);

    }



    @Transactional
    public void deleteById(Integer id) {

            // 删除客户信息
            contractMapper.deleteCustomerByContractNumber(id);

            // 删除合同
            contractMapper.deleteById(id);
        }
}


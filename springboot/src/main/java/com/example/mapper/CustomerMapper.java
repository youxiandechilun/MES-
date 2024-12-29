package com.example.mapper;

import com.example.entity.Contract;
import com.example.entity.Customer;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CustomerMapper {

    @Select("SELECT * FROM customers WHERE contract_number LIKE CONCAT('%', #{contractNumber}, '%') AND customer_name LIKE CONCAT('%', #{customerName}, '%') ORDER BY id DESC")
    List<Customer> selectAll(Customer customer);


}

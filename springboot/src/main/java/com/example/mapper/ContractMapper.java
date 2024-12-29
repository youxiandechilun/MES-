package com.example.mapper;

import com.example.entity.Contract;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ContractMapper {

    @Select("SELECT * FROM contracts WHERE contract_name LIKE CONCAT('%', #{contractName}, '%') AND contract_number LIKE CONCAT('%', #{contractNumber}, '%') ORDER BY id DESC")
    List<Contract> selectAll(Contract contract);

    @Insert("INSERT INTO contracts (contract_number, contract_name, business_opportunity, customer_code, customer_name, customer_phone, sales_order_generated, invoice_issued, total_amount, start_date, start_date_time, end_date_time, contract_type, party_a, party_b, remarks, execution_stage, attachment_url) " +
            "VALUES (#{contractNumber}, #{contractName}, #{businessOpportunity}, #{customerCode}, #{customerName}, #{customerPhone}, #{salesOrderGenerated}, #{invoiceIssued}, #{totalAmount}, #{startDate}, #{startDateTime}, #{endDateTime}, #{contractType}, #{partyA}, #{partyB}, #{remarks}, #{executionStage}, #{attachmentUrl})")
    void insert(Contract contract);

    @Update("UPDATE contracts SET contract_number=#{contractNumber}, contract_name=#{contractName}, business_opportunity=#{businessOpportunity}, customer_code=#{customerCode}, customer_name=#{customerName}, customer_phone=#{customerPhone}, sales_order_generated=#{salesOrderGenerated}, invoice_issued=#{invoiceIssued}, total_amount=#{totalAmount}, start_date=#{startDate}, start_date_time=#{startDateTime}, end_date_time=#{endDateTime}, contract_type=#{contractType}, party_a=#{partyA}, party_b=#{partyB}, remarks=#{remarks}, execution_stage=#{executionStage}, attachment_url=#{attachmentUrl} WHERE id=#{id} ")
    void updateById(Contract contract);


    @Update("UPDATE contracts SET  sales_order_generated=#{salesOrderGenerated},execution_stage=#{executionStage} WHERE contract_number=#{contractNumber}")
    void updateById2(Contract contract);

    @Update("UPDATE contracts SET  execution_stage=#{executionStage} WHERE contract_number=#{contractNumber}")
    void updateById3(Contract contract);

    @Delete("DELETE FROM contracts WHERE id=#{id}")
    void deleteById(Integer id);



    @Insert("INSERT INTO customers(contract_number, contract_name,customer_code, customer_name, customer_phone) " +
            "VALUES (#{contractNumber}, #{contractName},#{customerCode}, #{customerName}, #{customerPhone})")
    void insertCustomer(@Param("contractNumber") String contractNumber,
                        @Param("contractName") String contractName,
                        @Param("customerCode") String customerCode,
                        @Param("customerName") String customerName,
                        @Param("customerPhone") String customerPhone);

    @Update("UPDATE customers SET " +
            "contract_name=#{contractName}, " +
            "contract_number=#{contractNumber}, " +
            "customer_code=#{customerCode}, " +
            "customer_name=#{customerName}, " +
            "customer_phone=#{customerPhone} " +
            "WHERE id=#{id}")
    void updateCustomer(@Param("contractName") String contractName,
                        @Param("contractNumber") String contractNumber,
                        @Param("customerCode") String customerCode,
                        @Param("customerName") String customerName,
                        @Param("customerPhone") String customerPhone,
                        @Param("id") Integer id); // 将id参数正确地放置在参数列表中

    // 如果需要删除客户信息
    @Delete("DELETE FROM customers WHERE id=#{id}")
    void deleteCustomerByContractNumber(@Param("id") int id);


}
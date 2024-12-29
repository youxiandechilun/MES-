package com.example.mapper;

import com.example.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Select("SELECT * FROM orders " +
            "WHERE contractNumber LIKE CONCAT('%', #{contractNumber}, '%') " +
            "AND productName LIKE CONCAT('%', #{productName}, '%') " +
            "AND orderType = 'sale' " + // 添加这个条件来过滤orderType
            "ORDER BY id DESC")
    List<Order> selectAll(Order order);

    @Select("SELECT * FROM orders " +
            "WHERE contractNumber LIKE CONCAT('%', #{contractNumber}, '%') " +
            "AND productName LIKE CONCAT('%', #{productName}, '%') " +
            "AND orderType = 'purchase' " + // 添加这个条件来过滤orderType
            "ORDER BY id DESC")
    List<Order> selectAll2(Order order);

    @Insert("INSERT INTO orders (orderID, customerID, contractNumber, productName, startTime, endTime, status, totalAmount, productSum, orderType, generateProductionPlan) " +
            "VALUES (#{orderID}, #{customerID}, #{contractNumber}, #{productName}, #{startTime}, #{endTime}, #{status}, #{totalAmount}, #{productSum}, #{orderType}, '0')")
    void insert(Order order);

    @Update("UPDATE orders SET orderID=#{orderID}, customerID=#{customerID}, contractNumber=#{contractNumber}, productName=#{productName}, startTime=#{startTime}, endTime=#{endTime}, status=#{status}, totalAmount=#{totalAmount} WHERE id=#{id}")
    void updateById(Order order);

    @Update("UPDATE orders SET status='开始中', generateProductionPlan=1 WHERE contractNumber=#{contractNumber}")
    void updateById2(Order order);

    @Delete("DELETE FROM orders WHERE id=#{id}")
    void deleteById(Integer id);
}
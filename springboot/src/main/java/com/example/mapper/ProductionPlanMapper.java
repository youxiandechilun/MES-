package com.example.mapper;

import com.example.entity.ProductionPlan;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ProductionPlanMapper {

    @Select("SELECT * FROM production_plan " +
            "WHERE  orderID LIKE CONCAT('%', #{orderID}, '%') " +
            "AND productName LIKE CONCAT('%', #{productName}, '%') " +
            "ORDER BY id DESC")
    List<ProductionPlan> selectAll(ProductionPlan productionPlan);

    @Insert("INSERT INTO production_plan (planID, orderID, customerID, productName, productSum, startTime, endTime, status, totalAmount, remarks, productionProcesses, productionEquipments, materials) " +
            "VALUES (#{planID}, #{orderID}, #{customerID}, #{productName}, #{productSum}, #{startTime}, #{endTime}, #{status}, #{totalAmount}, #{remarks}, #{productionProcesses}, #{productionEquipments}, #{materials})")
    void insert(ProductionPlan productionPlan);

    @Update("UPDATE production_plan SET " +
            "planID=#{planID}, orderID=#{orderID}, customerID=#{customerID}, productName=#{productName}, productSum=#{productSum}, startTime=#{startTime}, endTime=#{endTime}, status=#{status}, totalAmount=#{totalAmount}, remarks=#{remarks}, productionProcesses=#{productionProcesses}, productionEquipments=#{productionEquipments}, materials=#{materials}, workshop=#{workshop} " +
            "WHERE id=#{id}")
    void updateById(ProductionPlan productionPlan);

    @Delete("DELETE FROM production_plan WHERE id=#{id}")
    void deleteById(Integer id);
}
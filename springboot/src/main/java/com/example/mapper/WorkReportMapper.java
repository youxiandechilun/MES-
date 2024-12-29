package com.example.mapper;

import com.example.entity.Contract;
import com.example.entity.WorkReport;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WorkReportMapper {


    @Select("SELECT * FROM workReport WHERE Worker LIKE CONCAT('%', #{worker}, '%')  and ProductName LIKE CONCAT('%',#{productName},'%')          ORDER BY ReportID ASC")
    List<WorkReport> selectAll(WorkReport workReport);


    @Insert("INSERT INTO workreport ( ProductName, ProductCode, Workshop, Team, Worker, CompletedQuantity, LossQuantity, NextProcess, Date) " +
            "VALUES (#{productName}, #{productCode}, #{workshop}, #{team}, #{worker}, #{completedQuantity}, #{lossQuantity}, #{nextProcess}, #{date})")
    void insert(WorkReport workReport);

}

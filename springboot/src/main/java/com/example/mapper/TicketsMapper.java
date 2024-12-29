package com.example.mapper;

import com.example.entity.Tickets;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TicketsMapper {


    @Select("SELECT * FROM tickets  where ticketID like concat('%',#{ticketID},'%') and worker like concat('%',#{worker},'%')ORDER BY ID DESC")
    List<Tickets> selectAll(Tickets tickets);

    @Insert("INSERT INTO tickets (ticketID, planID, productName, workshop, team, worker, grade, process, number, startDate, endDate) " +
            "VALUES (#{ticketID}, #{planID}, #{productName}, #{workshop}, #{team}, #{worker}, #{grade}, #{process}, #{number}, #{startDate}, #{endDate})")
    void insert(Tickets tickets);

    @Update("UPDATE tickets SET " +
            "ticketID = :ticketID, " +
            "planID = :planID, " +
            "productName = :productName, " +
            "workshop = :workshop, " +
            "team = :team, " +
            "worker = :worker, " +
            "grade = :grade, " +
            "process = :process, " +
            "number = :number, " +
            "startDate = :startDate, " +
            "endDate = :endDate " +
            "WHERE ticketID = :ticketID") // 假设使用ticketID作为更新条件
    void updateById(Tickets tickets);

    @Delete("delete from tickets where id=#{id}")
    void deleteById(Integer id);


}

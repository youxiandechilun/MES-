package com.example.mapper;
import com.example.entity.Employees;
import org.apache.ibatis.annotations.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeesMapper {

    @Insert("INSERT INTO employees (no, username, password, name, workshop, teamsGroups, sex, seniority, grade, avatar, history, role) " +
            "VALUES (#{no}, #{username}, #{password}, #{name}, #{workshop}, #{teamsGroups}, #{sex}, #{seniority}, #{grade}, #{avatar}, #{history}, #{role})")
    int registerEmployee(
            @Param("no") String no,
            @Param("username") String username,
            @Param("password") String password,
            @Param("name") String name,
            @Param("workshop") String workshop,
            @Param("teamsGroups") String teamsGroups,
            @Param("sex") String sex,
            @Param("seniority") Date seniority, // 移除了@JsonFormat注解
            @Param("grade") int grade,
            @Param("avatar") String avatar,
            @Param("history") String history,
            @Param("role") String role
    );

//    @Select("SELECT name AND history FROM employees " +
//            "WHERE workshop LIKE CONCAT('%', #{workshop}, '%') " +
//            "AND teamsGroups LIKE CONCAT('%', #{teamsGroups}, '%') " +
//            "ORDER BY id DESC")
//    List<String> selectname(String workshop,String teamsGroups);


    @Select("SELECT name, history,grade FROM employees " +
            "WHERE workshop LIKE CONCAT('%', #{workshop}, '%') " +
            "AND teamsGroups LIKE CONCAT('%', #{teamsGroups}, '%') " +
            "ORDER BY id DESC")
    List<Map<String, Object>> selectname(@Param("workshop") String workshop, @Param("teamsGroups") String teamsGroups);


    @Insert("INSERT INTO user ( username, password, name, role) " +
            "VALUES (#{username}, #{password}, #{name}, #{role})")
    int registerUser(

            @Param("username") String username,
            @Param("password") String password,
            @Param("name") String name,
            @Param("role") String role
    );


    @Select("SELECT * FROM employees " +
            "WHERE workshop LIKE CONCAT('%', #{workshop}, '%') " +
            "AND teamsGroups LIKE CONCAT('%', #{teamsGroups}, '%') " +
            "ORDER BY id DESC")
    List<Employees> selectAll(Employees employees);



    @Update("UPDATE orders SET orderID=#{orderID}, customerID=#{customerID}, contractNumber=#{contractNumber}, productName=#{productName}, startTime=#{startTime}, endTime=#{endTime}, status=#{status}, totalAmount=#{totalAmount} WHERE id=#{id}")
    void updateById(Employees employees);


    @Delete("DELETE FROM orders WHERE id=#{id}")
    void deleteById(Integer id);



}

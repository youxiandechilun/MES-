package com.example.mapper;

import com.example.entity.Material;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MaterialMapper {

    @Select("SELECT * FROM materials " +
            "WHERE material_name LIKE CONCAT('%', #{materialName}, '%') " +
            "AND material_type LIKE CONCAT('%', #{materialType}, '%') " +
            "ORDER BY id DESC")
    List<Material> selectAll(Material material);

    @Insert("INSERT INTO materials (material_name, material_type, material_code, quantity, unit_cost,material_category) " +
            "VALUES (#{materialName}, #{materialType}, #{materialCode}, #{quantity}, #{unitCost},#{materialCategory})")
    void insert(Material material);

    @Update("UPDATE materials SET " +
            "material_name=#{materialName}, " +
            "material_type=#{materialType}, " +
            "material_code=#{materialCode}, " +
            "quantity=#{quantity}, " +
            "unit_cost=#{unitCost}, " +
            "material_category=#{materialCategory} " +
            "WHERE id=#{id}")
    void updateById(Material material);

    @Delete("DELETE FROM materials WHERE id=#{id}")
    void deleteById(Integer id);
}
package com.example.mapper;

import com.example.entity.Contract;
import com.example.entity.DeviceRecord;
import com.example.entity.MaterialRecords;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MaterialRecordsMapper {



    @Select("SELECT * FROM material_issuance_records WHERE material_name LIKE CONCAT('%',#{materialName},'%') AND  worker LIKE CONCAT('%', #{worker}, '%')  ORDER BY record_id DESC")
    List<MaterialRecords> selectAll(MaterialRecords materialRecords);





    @Insert("INSERT INTO material_issuance_records (material_id,material_name, worker, issuance_date, quantity_issued, purpose) " +
            "VALUES (#{materialId},#{materialName}, #{worker}, #{issuanceDate}, #{quantityIssued}, #{purpose})")
    void insert(MaterialRecords materialRecords);

}

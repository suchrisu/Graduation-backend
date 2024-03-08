package com.chrisu.mapper;

import com.chrisu.POJO.RolePower;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface RolePowerMapper {

  @Insert("insert into role_power(role_power_id,role_power_name)values(#{rolePowerId},#{rolePowerName})")
  void save(RolePower rolePower);

  @Delete("delete from role_power where role_power_id = #{rolePowerId} ")
  void delete(int rolePowerId);

  @Update("update role_power set role_power_name = #{rolePowerName} where role_power_id = #{rolePowerId} ")
  void update(RolePower rolePower);

  @Select("select * from role_power")
  @Results(id = "rolePowerResultMap", value = {
      @Result(column = "role_power_id", property = "rolePowerId"),
      @Result(column = "role_power_name", property = "rolePowerName")
  })
  List<RolePower> findAll();

  @Select("select * from role_power where role_power_id = #{rolePowerId} ")
  @ResultMap("rolePowerResultMap")
  RolePower findById(int rolePowerId);
}

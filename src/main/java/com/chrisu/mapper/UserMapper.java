package com.chrisu.mapper;

import com.chrisu.POJO.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

  @Insert("insert into user(user_id,role_power_id,user_name,user_register_time," +
      "user_password,user_head_picture_address)values(#{userId},#{rolePowerId}," +
      "#{userName},#{userRegisterTime},#{userPassword},#{userHeadPictureAddress})")
  int save(User user);

  @Delete("delete from user where user_id = #{userId}")
  int deleteById(String userId);


  int update(User user);

  @Select("select * from user where role_power_id = #{rolePowerId} limit #{begin},#{pageSize}")
  @ResultMap("userResultMap")
  List<User> findUsersByPage(@Param("rolePowerId") int rolePowerId, @Param("begin") int begin,
      @Param("pageSize") int pageSize);


  @Select("select * from user where role_power_id = #{rolePowerId} and user_id like #{userId} limit #{begin},#{pageSize}")
  @ResultMap("userResultMap")
  List<User> findUsersByIdByPage(@Param("userId") String userId,
      @Param("rolePowerId") int rolePowerId, @Param("begin") int begin,
      @Param("pageSize") int pageSize);

  @Select("select * from user where role_power_id = '2' OR role_power_id = '3' limit #{begin},#{pageSize}")
  List<User> findCheckerAndCreatorByPage(@Param("begin") int begin, @Param("pageSize") int pageSize);

  @Select("select * from user where ( role_power_id = '2' OR role_power_id = '3') and user_id like #{userId} "
      + "limit #{begin},#{pageSize}")
  List<User> findCheckerAndCreatorByIdByPage(@Param("userId") String userId,@Param("begin") int begin,
      @Param("pageSize") int pageSize);

  @Select("select * from user where user_id = #{userId}")
  @ResultMap("userResultMap")
  User findById(String userId);

  @Select("select count(*) from user where role_power_id = #{rolePowerId}")
  int getTotalCount(@Param("rolePowerId") int rolePowerId);

  @Select("select count(*) from user where user_id like #{userId} and role_power_id = #{rolePowerId} ")
  int getTotalCountById(@Param("userId") String userId, @Param("rolePowerId") int rolePowerId);

  int deleteByIds(@Param("ids") String[] ids);



}

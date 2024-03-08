package com.chrisu.mapper;

import com.chrisu.POJO.Login;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作登录记录表的mapper
 */
@Mapper
public interface LoginMapper {

  @Insert("insert into login values (#{loginTime},#{userId})")
  int insertLogin(Login login);

  int getTotalcount();
}

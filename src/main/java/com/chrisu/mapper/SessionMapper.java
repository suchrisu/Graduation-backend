package com.chrisu.mapper;

import com.chrisu.POJO.Session;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SessionMapper {

  @Insert(
      "insert into session (session_id, session_owner, session_name, session_file, "
          + "session_last_time) VALUES (#{sessionId},#{sessionOwner},#{sessionName},"
          + "#{sessionFile},#{sessionLastTime})")
  int save(Session session);

  @Delete("delete from session where session_id = #{sessionId}")
  int deleteById(String sessionId);

  @Update(
      "update session set session_name=#{sessionName} where session_id = #{sessionId}")
  int updateName(Session session);

  @Update(
      "update session set session_last_time=#{sessionLastTime} where session_id = #{sessionId}"
  )
  int updateTime(Session session);

  @Select("select * from session where session_owner = #{Owner}")
  List<Session> getSessionsByOwner(String Owner);


}

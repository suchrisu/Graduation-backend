package com.chrisu.service.impl;

import com.chrisu.POJO.Session;
import com.chrisu.controller.Result;
import com.chrisu.mapper.SessionMapper;
import com.chrisu.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class sessionServiceImpl implements SessionService {

  @Autowired private SessionMapper sessionMapper;

  @Override
  public Result addSession(Session session) {
    if (sessionMapper.save(session) > 0) {
      return Result.success(null);
    }
    return Result.error("添加失败!");
  }

  @Override
  public Result removeSession(String sessionId) {
    if (sessionMapper.deleteById(sessionId) > 0) {
      return Result.success(null);
    }
    return Result.error("删除失败!");
  }

  @Override
  public Result setSessionName(Session session) {
    if (sessionMapper.updateName(session) > 0) {
      return Result.success(null);
    }
    return Result.error("修改失败!");
  }

  @Override
  public Result setSessionTime(Session session) {
    if (sessionMapper.updateTime(session) > 0) {
      return Result.success(null);
    }
    return Result.error("修改失败!");
  }

  @Override
  public Result getSessions(String owner) {
    return Result.success(sessionMapper.getSessionsByOwner(owner));
  }
}

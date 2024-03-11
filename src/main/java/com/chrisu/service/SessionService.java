package com.chrisu.service;

import com.chrisu.POJO.Session;
import com.chrisu.controller.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public interface SessionService {

  Result addSession(Session session);

  Result removeSession(String sessionId);

  Result setSessionName(Session session);

  Result setSessionTime(Session session);

  Result getSessions(String owner);
}

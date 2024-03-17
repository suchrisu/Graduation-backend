package com.chrisu.controller;

import com.chrisu.POJO.Session;
import com.chrisu.POJO.User;
import com.chrisu.service.SessionService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class SessionController {
  @Autowired
  SessionService sessionService;

  @PostMapping("/addSession")
  Result addSession( @RequestBody Session session){
    return sessionService.addSession(session);
  }

  @GetMapping("/removeSession")
  Result removeSession(HttpServletRequest request){
    String sessionId = request.getParameter("sessionId");
    System.out.println(sessionId);
    return sessionService.removeSession(sessionId);
  }

  @PostMapping("/setSessionName")
  Result setSessionName(@RequestBody Session session){
    return sessionService.setSessionName(session);
  }

  @PostMapping("/setSessionTime")
  Result setSessionTime(@RequestBody Session session){
    return sessionService.setSessionTime(session);
  }

  @GetMapping("/getSessions")
  Result getSessions(HttpServletRequest request){
    User currentUser = CurrentUserUtil.getCurrentUser(request.getSession());
    return sessionService.getSessions(currentUser.getUserId());
  }
}

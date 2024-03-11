package com.chrisu.controller;

import com.chrisu.POJO.Session;
import com.chrisu.POJO.User;
import com.chrisu.service.SessionService;
import com.chrisu.utils.TimeStampUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
  Result addSession( HttpServletRequest request,@RequestBody Session session){
    User currentUser = (User) request.getSession().getAttribute("user");
    String sessionId = currentUser.getUserId()+"_"+ TimeStampUtil.getTimeStamp();
  }
}

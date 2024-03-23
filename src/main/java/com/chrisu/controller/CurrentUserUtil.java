package com.chrisu.controller;

import com.chrisu.POJO.User;
import javax.servlet.http.HttpSession;

public class CurrentUserUtil {
  public static User getCurrentUser(HttpSession httpSession){
    User user = (User) httpSession.getAttribute("currentUser");
    if(user==null){
      throw new RuntimeException("登录已过期！");
    }
    return user;
  }

  public static void setCurrentUser(HttpSession httpSession,User user){
    httpSession.setAttribute("currentUser",user);
  }

  public static void removeCurrentUser(HttpSession httpSession){
    httpSession.removeAttribute("currentUser");
  }
}

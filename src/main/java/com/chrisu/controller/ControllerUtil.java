package com.chrisu.controller;

import com.chrisu.POJO.User;
import javax.servlet.http.HttpSession;

public class ControllerUtil {
  public static User getCurrentUser(HttpSession httpSession){
    return (User) httpSession.getAttribute("user");
  }
}

package com.chrisu.POJO;

import org.springframework.stereotype.Component;


/**
 * 登录记录表对应的实体类
 */
@Component
public class Login {

  private String loginTime; // 登录时间
  private String userId; // 登录的用户id

  public Login() {
  }

  public Login(String loginTime, String userId) {
    this.loginTime = loginTime;
    this.userId = userId;
  }

  public String getLoginTime() {
    return loginTime;
  }

  public void setLoginTime(String loginTime) {
    this.loginTime = loginTime;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "Login{" +
        "loginTime='" + loginTime + '\'' +
        ", userId='" + userId + '\'' +
        '}';
  }
}

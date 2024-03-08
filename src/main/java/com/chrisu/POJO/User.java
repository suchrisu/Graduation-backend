package com.chrisu.POJO;

import java.io.Serializable;
import org.springframework.stereotype.Component;

/**
 * 用户表对应的实体类
 */
@Component
public class User implements Serializable {

  private String userId; // 用户id
  private int rolePowerId; // 用户权限id
  private String userName; // 用户名
  private String userRegisterTime; // 用户注册的时间
  private String userPassword; // 用户密码
  private String userHeadPictureAddress; // 用户头像的图片地址

  public User() {
  }

  public User(String userId, int rolePowerId, String userName,
      String userRegisterTime, String userPassword,
      String userHeadPictureAddress) {
    this.userId = userId;
    this.rolePowerId = rolePowerId;
    this.userName = userName;
    this.userRegisterTime = userRegisterTime;
    this.userPassword = userPassword;
    this.userHeadPictureAddress = userHeadPictureAddress;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public int getRolePowerId() {
    return rolePowerId;
  }

  public void setRolePowerId(int rolePowerId) {
    this.rolePowerId = rolePowerId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserRegisterTime() {
    return userRegisterTime;
  }

  public void setUserRegisterTime(String userRegisterTime) {
    this.userRegisterTime = userRegisterTime;
  }

  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }

  public String getUserHeadPictureAddress() {
    return userHeadPictureAddress;
  }

  public void setUserHeadPictureAddress(String userHeadPictureAddress) {
    this.userHeadPictureAddress = userHeadPictureAddress;
  }

  @Override
  public String toString() {
    return "User{" +
        "userId='" + userId + '\'' +
        ", rolePowerId=" + rolePowerId +
        ", userName='" + userName + '\'' +
        ", userRegisterTime='" + userRegisterTime + '\'' +
        ", userPassword='" + userPassword + '\'' +
        ", userHeadPictureAddress='" + userHeadPictureAddress + '\'' +
        '}';
  }
}

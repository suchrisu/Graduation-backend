package com.chrisu.POJO;

import java.io.Serializable;
import org.springframework.stereotype.Component;

/**
 * 用户权限表对应的实体类
 */
@Component
public class RolePower implements Serializable {

  private int rolePowerId; // 用户权限id
  private String rolePowerName; // 用户权限（用户类型）

  public static final int MANAGER = 1; // 系统管理员权限id
  public static final int CREATOR = 2; // 模板创建者权限id
  public static final int CHECKER = 3; // 模板审核员权限id
  public static final int USER = 4; // 普通用户权限id

  public RolePower() {
  }

  public RolePower(int rolePowerId, String rolePowerName) {
    this.rolePowerId = rolePowerId;
    this.rolePowerName = rolePowerName;
  }

  public int getRolePowerId() {
    return rolePowerId;
  }


  public String getRolePowerName() {
    return rolePowerName;
  }

  public void setRolePowerId(int rolePowerId) {
    this.rolePowerId = rolePowerId;
  }

  public void setRolePowerName(String rolePowerName) {
    this.rolePowerName = rolePowerName;
  }

  @Override
  public String toString() {
    return "RolePower{" +
        "rolePowerId=" + rolePowerId +
        ", rolePowerName='" + rolePowerName + '\'' +
        '}';
  }
}

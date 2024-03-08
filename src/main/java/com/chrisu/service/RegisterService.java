package com.chrisu.service;


import com.chrisu.POJO.User;
import com.chrisu.controller.Result;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管理注册业务的接口
 */
@Transactional
public interface RegisterService {

  /**
   * 注册
   *
   * @param user 注册的用户
   * @return
   */
  Result register(User user);

  /**
   * 发送注册码
   *
   * @param mail 注册的邮箱
   * @return
   */
  Result sendRegisterCode(String mail);
}

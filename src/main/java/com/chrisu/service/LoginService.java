package com.chrisu.service;


import com.chrisu.POJO.Login;
import com.chrisu.POJO.User;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管理登录业务的接口
 */
@Transactional
public interface LoginService {

  /**
   * 添加登录记录
   *
   * @param login
   * @return
   */
  int insertLogin(Login login);

  /**
   * 根据用户id查询用户
   *
   * @param user
   * @return
   */
  User findById(User user);
}

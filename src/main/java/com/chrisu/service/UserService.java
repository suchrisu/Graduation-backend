package com.chrisu.service;


import com.chrisu.POJO.User;
import com.chrisu.controller.Result;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管理用户管理业务的接口
 */
@Transactional
public interface UserService {

  /**
   * 分页查询用户
   *
   * @param rolePowerId 用户类型
   * @param begin       从哪条记录开始查询
   * @param pageSize    页的大小
   * @return
   */
  Result getUsersByPage(int rolePowerId, int begin, int pageSize);

  /**
   * 按照id模糊分页查询用户
   *
   * @param userId      查询的用户id
   * @param rolePowerId 用户类型
   * @param begin       从哪条记录开始查询
   * @param pageSize    页的大小
   * @return
   */
  Result getUsersByIdByPage(String userId, int rolePowerId, int begin, int pageSize);



  /**
   * 修改用户
   *
   * @param user      修改的用户
   * @return
   */
  Result update(User user);

  /**
   * 根据用户id删除用户
   *
   * @param userId    要删除的用户id
   * @return
   */
  Result deleteById(String userId );

  /**
   * 批量删除用户
   *
   * @param userIds   要删除的用户id数组
   * @return
   */
  Result deleteByIds(String[] userIds);

  /**
   * 根据用户id准确查询用户
   *
   * @param userId 查询的用户id
   * @return
   */
  Result getUserById(String userId);


  /**
   * 发送验证码
   * @param mail 发送的用户邮箱
   * @return
   */
  Result sendValidCode(String mail);

}

package com.chrisu.service.impl;


import com.chrisu.POJO.PageBean;
import com.chrisu.POJO.RolePower;
import com.chrisu.POJO.User;
import com.chrisu.controller.Result;
import com.chrisu.mapper.UserMapper;
import com.chrisu.service.UserService;
import com.chrisu.utils.TimeStampUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


  @Autowired
  private UserMapper userMapper;

  @Override
  public Result getUsersByPage(int rolePowerId, int begin, int pageSize) {
    int totalCount = userMapper.getTotalCount(rolePowerId);
    List<User> users = userMapper.findUsersByPage(rolePowerId, begin, pageSize);
    PageBean<User> pageBean = new PageBean<>(totalCount, users);
    return Result.success(pageBean);
  }

  @Override
  public Result getUsersByIdByPage(String userId, int rolePowerId, int begin, int pageSize) {
    userId = "%" + userId + "%";
    List<User> users = userMapper.findUsersByIdByPage(userId, rolePowerId, begin, pageSize);
    int totalCount = userMapper.getTotalCountById(userId, rolePowerId);
    PageBean<User> pageBean = new PageBean<>(totalCount, users);
    return Result.success(pageBean);
  }



  @Override
  public Result update(User user) {
    if (userMapper.update(user) > 0) {
      return Result.success(true);
    }
    return Result.error("修改失败,请稍后重试!");
  }

  @Override
  public Result deleteById(String userId ) {
    if (userMapper.deleteById(userId) > 0) {
      return Result.success(true);
    }
    return Result.error("删除失败,请稍后重试!");
  }

  @Override
  public Result deleteByIds(String[] userIds) {

    if (userMapper.deleteByIds(userIds) > 0) {
      return Result.success(true);
    }
    return Result.error("删除失败,请稍后重试!");


  }

  @Override
  public Result getUserById(String userId) {
    User user = userMapper.findById(userId);
    if (user == null) {
      return Result.error("请先登录！");
    }
    return Result.success(user);
  }


}

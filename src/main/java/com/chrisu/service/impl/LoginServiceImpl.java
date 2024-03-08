package com.chrisu.service.impl;



import com.chrisu.POJO.Login;
import com.chrisu.POJO.User;
import com.chrisu.mapper.LoginMapper;
import com.chrisu.mapper.UserMapper;
import com.chrisu.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

  @Autowired
  LoginMapper loginMapper;
  @Autowired
  UserMapper userMapper;

  @Override
  public int insertLogin(Login login) {
    return loginMapper.insertLogin(login);
  }

  @Override
  public User findById(User user) {
    return userMapper.findById(user.getUserId());
  }
}

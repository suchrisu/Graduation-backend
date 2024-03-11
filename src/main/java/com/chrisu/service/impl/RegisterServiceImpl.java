package com.chrisu.service.impl;


import com.chrisu.POJO.RolePower;
import com.chrisu.POJO.User;
import com.chrisu.controller.Result;
import com.chrisu.mapper.UserMapper;
import com.chrisu.service.RegisterService;
import com.chrisu.utils.RandomCode;
import com.chrisu.utils.SendMailUtil;
import com.chrisu.utils.TimeStampUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

  @Autowired
  UserMapper userMapper;

  @Override
  public Result register(User user) {
    User registerUser = userMapper.findById(user.getUserId());
    if (registerUser != null) {
      return Result.error("该用户已存在!");
    }
    user.setRolePowerId(RolePower.USER);
    String registerTime = TimeStampUtil.getTimeStamp();
    user.setUserRegisterTime(registerTime);
    if (userMapper.save(user) > 0) {
      return Result.success(user);
    }
    return Result.error("注册失败,请稍后重试!");
  }

  @Override
  public Result sendRegisterCode(String mail) {

    User user = userMapper.findById(mail);
    if(user != null){
      return Result.error("用户已存在!");
    }
    String registerCode = RandomCode.getRandomCode();
    boolean isSendSuccess = SendMailUtil.sendEmail(SendMailUtil.FROM_MAIL,
        SendMailUtil.FROM_MAIL_PASSWORD, mail, registerCode, mail);
    if (isSendSuccess) {
      return Result.success(registerCode);
    }
    return Result.error("请输入正确的邮箱！");
  }
}

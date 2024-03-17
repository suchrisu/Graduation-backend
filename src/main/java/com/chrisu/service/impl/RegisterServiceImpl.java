package com.chrisu.service.impl;


import com.chrisu.POJO.RolePower;
import com.chrisu.POJO.User;
import com.chrisu.controller.Result;
import com.chrisu.mapper.UserMapper;
import com.chrisu.service.RegisterService;
import com.chrisu.utils.Md5Util;
import com.chrisu.utils.RandomCode;
import com.chrisu.utils.SendMailUtil;
import com.chrisu.utils.TimeStampUtil;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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
    String userPassword = Md5Util.md5(user.getUserPassword());
    user.setUserPassword(userPassword);
    user.setuserHeader("defaultHeader.png");
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
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    String str = "<!DOCTYPE html><html><head><meta charset='UTF-8'></head><body>" +
        "<p style='font-size: 20px;font-weight:bold;'>尊敬的："
        + mail
        + "，您好！</p>"
        + "<p style='text-indent:2em; font-size: 20px;'>" +
        "欢迎使用智慧政务交互平台，您本次的验证码是 "
        + "<span style='font-size:30px;font-weight:bold;color:red'>" + registerCode
        + "</span>，请尽快使用！</p>"
        + "<p style='text-align:right; padding-right: 20px;'"
        + "<a href='http://www.hyycinfo.com' style='font-size: 18px'>chrisu</a></p>"
        + "<span style='font-size: 18px; float:right; margin-right: 60px;'>"
        + sdf.format(new Date())
        + "</span></body></html>";
    boolean isSendSuccess = SendMailUtil.sendEmail(SendMailUtil.FROM_MAIL,
        SendMailUtil.FROM_MAIL_PASSWORD, mail, str);
    if (isSendSuccess) {
      return Result.success(registerCode);
    }
    return Result.error("请输入正确的邮箱！");
  }
}

package com.chrisu.service.impl;


import com.chrisu.POJO.PageBean;
import com.chrisu.POJO.RolePower;
import com.chrisu.POJO.User;
import com.chrisu.controller.Result;
import com.chrisu.mapper.UserMapper;
import com.chrisu.service.UserService;
import com.chrisu.utils.RandomCode;
import com.chrisu.utils.SendMailUtil;
import com.chrisu.utils.TimeStampUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpSession;
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

  @Override
  public Result   sendValidCode(String mail){
    User user = userMapper.findById(mail);
    String validCode = RandomCode.getRandomCode();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    String str = "<!DOCTYPE html><html><head><meta charset='UTF-8'></head><body>" +
        "<p style='font-size: 20px;font-weight:bold;'>尊敬的："
        + user.getUserName()
        + "，您好！</p>"
        + "<p style='text-indent:2em; font-size: 20px;'>" +
        "欢迎使用智慧政务交互平台，您本次的验证码是 "
        + "<span style='font-size:30px;font-weight:bold;color:red'>" + validCode
        + "</span>，请尽快使用！</p>"
        + "<p style='text-align:right; padding-right: 20px;'"
        + "<a href='http://www.hyycinfo.com' style='font-size: 18px'>chrisu</a></p>"
        + "<span style='font-size: 18px; float:right; margin-right: 60px;'>"
        + sdf.format(new Date())
        + "</span></body></html>";
    boolean isSendSuccess = SendMailUtil.sendEmail(SendMailUtil.FROM_MAIL,
        SendMailUtil.FROM_MAIL_PASSWORD, mail, str);
    if (isSendSuccess) {
      return Result.success(validCode);
    }
    return Result.error("发送失败,请稍后重试!");
  }

}

package com.chrisu.service.impl;



import com.chrisu.POJO.Login;
import com.chrisu.POJO.User;
import com.chrisu.controller.CurrentUserUtil;
import com.chrisu.controller.Result;
import com.chrisu.mapper.LoginMapper;
import com.chrisu.mapper.UserMapper;
import com.chrisu.service.LoginService;
import com.chrisu.utils.Md5Util;
import com.chrisu.utils.TimeStampUtil;
import com.chrisu.utils.TokenUtil;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Slf4j
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

  @Override
  public Result login(HttpSession session,User user){

    User loginUser = findById(user);
    if (loginUser == null) {
      return Result.error("此用户不存在!");
    }
    String userPassword = Md5Util.md5(user.getUserPassword());

    if (!userPassword.equals(loginUser.getUserPassword())) {
      return Result.error("密码错误!");
    }
    String token = TokenUtil.sign(loginUser);
    CurrentUserUtil.setCurrentUser(session,loginUser);
    Login login = new Login(TimeStampUtil.getTimeStamp(), user.getUserId());
    insertLogin(login);
    Result result = Result.success(loginUser);
    result.setMessage(token);
    return result;
  }
}

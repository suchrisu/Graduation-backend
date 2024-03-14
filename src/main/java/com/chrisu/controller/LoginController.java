package com.chrisu.controller;


import com.chrisu.POJO.Login;
import com.chrisu.POJO.User;
import com.chrisu.service.LoginService;
import com.chrisu.utils.TimeStampUtil;
import com.chrisu.utils.VerifyCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 处理登录相关请求的controller
 */
@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class LoginController {

  @Autowired
  private LoginService loginService;

  /**
   * 登录
   *
   * @param request
   * @param user
   * @return
   */
  @PostMapping("/login")
  public Result login(HttpServletRequest request, HttpServletResponse response,
      @RequestBody User user, @RequestParam String code) {
    HttpSession session = request.getSession();

    log.info((String) session.getAttribute("loginCode"));
    if (!code.equalsIgnoreCase((String) session.getAttribute("loginCode"))) {
      return Result.error("验证码错误!");
    }
    User loginUser = loginService.findById(user);
    if (loginUser == null) {
      return Result.error("此用户不存在!");
    }
    if (!user.getUserPassword().equals(loginUser.getUserPassword())) {
      return Result.error("密码错误!");
    }
    session.setAttribute("user", loginUser);
    Login login = new Login(TimeStampUtil.getTimeStamp(), user.getUserId());
    loginService.insertLogin(login);
    return Result.success(loginUser);
  }


  /**
   * 登录获取验证码图片
   *
   * @param request
   * @param response
   * @throws IOException
   */
  @GetMapping("/loginCode")
  public void getLoginCode(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    VerifyCode verifyCode = new VerifyCode();
    BufferedImage image = verifyCode.getImage();
    response.setContentType("image/JPEG");
    HttpSession session = request.getSession();
    session.setAttribute("loginCode", verifyCode.getText());
    System.out.println(verifyCode.getText());
    VerifyCode.output(image, response.getOutputStream());
  }

  @GetMapping("/logOut")
  public Result logOut(HttpServletRequest request){
    HttpSession session = request.getSession();
    session.removeAttribute("user");
    return Result.success(true);
  }
}

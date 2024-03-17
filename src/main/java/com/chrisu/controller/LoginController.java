package com.chrisu.controller;


import com.alibaba.fastjson.JSON;
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
@RequestMapping("/login")
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
  public Result login(HttpServletRequest request, @RequestBody User user, @RequestParam String code) {
    HttpSession session = request.getSession();
    log.info((String) session.getAttribute("loginCode"));
    if (!code.equalsIgnoreCase((String) session.getAttribute("loginCode"))) {
      return Result.error("验证码错误!");
    }
    return loginService.login(session,user);
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
    CurrentUserUtil.removeCurrentUser(session);
    return Result.success(true);
  }
}

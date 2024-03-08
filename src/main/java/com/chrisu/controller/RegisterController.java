package com.chrisu.controller;

import com.chrisu.POJO.User;
import com.chrisu.service.RegisterService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理注册相关请求的controller
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class RegisterController {

  @Autowired
  RegisterService registerService;

  /**
   * 处理注册请求
   *
   * @param request
   * @param user    注册的用户
   * @param code    注册码
   * @return
   */
  @PostMapping("/register")
  public Result register(HttpServletRequest request, @RequestBody User user,
      @RequestParam("code") String code) {
    HttpSession session = request.getSession();
//    response.setHeader("Access-Control-Allow-Origin","*");
//    response.setHeader("Access-Control-Allow-Headers","*");
    System.out.println(session.getAttribute("registerCode"));
    if (!code.equals(session.getAttribute("registerCode"))) {
      return Result.error("注册码错误!");
    }
    return registerService.register(user);
  }

  /**
   * 处理发送注册码请求
   *
   * @param request
   * @param mail    当前注册的邮箱
   * @return
   */
  @GetMapping("/sendRegisterCode")
  public Result sendRegisterCode(HttpServletRequest request, @RequestParam("mail") String mail) {
    Result result = registerService.sendRegisterCode(mail);
    if (result.getCode() == 1) {
      HttpSession session = request.getSession();
      session.setAttribute("registerCode", result.getData());
    }
    return result;
  }

}

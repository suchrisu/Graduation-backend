package com.chrisu.controller;

import com.chrisu.POJO.User;
import com.chrisu.service.UserService;
import com.chrisu.utils.Md5Util;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 处理管理用户请求的controller
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class UserController {

  @Autowired
  private UserService userService;

  /**
   * 处理分页获取用户的请求
   *
   * @param currentPage 当前页码
   * @param pageSize    页的大小
   * @param rolePowerId 要获取的用户类型对应的id
   * @return
   */
  @GetMapping("/getUsersByPage")
  public Result getUsersByPage(@RequestParam("currentPage") int currentPage,
      @RequestParam("pageSize") int pageSize, @RequestParam("rolePowerId") int rolePowerId) {
    int begin = (currentPage - 1) * pageSize;
    return userService.getUsersByPage(rolePowerId, begin, pageSize);
  }

  /**
   * 处理按照id模糊查询分页获取用户的请求
   *
   * @param userId      查询的用户id
   * @param rolePowerId 要获取的用户类型对应的id
   * @param currentPage 当前页码
   * @param pageSize    页的大小
   * @return
   */
  @GetMapping("/getUsersByIdByPage")
  public Result getUsersByIdByPage(@RequestParam("userId") String userId,
      @RequestParam("rolePowerId") int rolePowerId, @RequestParam("currentPage") int currentPage,
      @RequestParam("pageSize") int pageSize) {
    int begin = (currentPage - 1) * pageSize;
    return userService.getUsersByIdByPage(userId, rolePowerId, begin, pageSize);
  }



  /**
   * 处理修改用户请求
   *
   * @param user    修改的用户
   * @return
   */
  @PostMapping("/updateUser")
  public Result updateUser(@RequestBody User user) {
    return userService.update(user);
  }

  /**
   * 处理根据用户id删除用户的请求
   *
   * @param request
   * @param userId  要删除的用户对应的用户id
   * @return
   */
  @GetMapping("/deleteById")
  public Result deleteById(HttpServletRequest request, @RequestParam("userId") String userId) {
    HttpSession session = request.getSession();
    User currentUser = (User) session.getAttribute("user");
    return userService.deleteById(userId);
  }

  /**
   * 处理批量删除用户的请求
   *
   * @param request
   * @param userIds 要删除的用户对应的用户id组成的数组
   * @return
   */
  @PutMapping("/deleteByIds")
  public Result deleteByIds(HttpServletRequest request, @RequestBody String[] userIds) {
    HttpSession session = request.getSession();
    User currentUser = (User) session.getAttribute("user");
    return userService.deleteByIds(userIds);
  }

  /**
   * 处理获取当前用户的请求
   *
   * @param request
   * @return
   */
  @GetMapping("/getCurrentUser")
  public Result getCurrentUser(HttpServletRequest request) {
    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");
    if (user == null) {
      return Result.error("请先登录!");
    }
    return userService.getUserById(user.getUserId());
  }

  /**
   * 处理发送验证码请求
   *
   * @param request
   * @param mail    发送的邮箱
   * @return
   */
  @GetMapping("/sendValidCode")
  public Result sendRegisterCode( HttpServletRequest request,@RequestParam("mail") String mail) {
    Result result = userService.sendValidCode(mail);
    if (result.getCode() == 1) {
      HttpSession session = request.getSession();
      session.setAttribute("validCode", result.getData());
    }
    return result;
  }


  @PostMapping("/updateUserPassword")
  public Result updateUserPassword(HttpServletRequest request,@RequestBody User user){
    String validCode = request.getParameter("code");
    HttpSession session = request.getSession();
    if(validCode.equals(session.getAttribute("validCode"))){
      String userPassword = Md5Util.md5(user.getUserPassword());
      user.setUserPassword(userPassword);
      return userService.update(user);
    }
    return Result.error("验证码错误!");
  }



}

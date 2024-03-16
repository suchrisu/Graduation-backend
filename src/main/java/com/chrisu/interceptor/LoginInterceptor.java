package com.chrisu.interceptor;

import com.chrisu.POJO.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

  private final String[] context = {
      "/manager",
      "/user"
  };

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    System.out.println("preHandle....");
    HttpSession session = request.getSession();
    User user = (User) session.getAttribute("user");
    if (user == null) {
      return false;
    }
    int rolePowerId = user.getRolePowerId();
    String uri = request.getRequestURI();
    for (int i = 0; i < context.length; i++) {
      if (uri.startsWith(context[i])) {
        if (rolePowerId + 1 == i) {
          return true;
        }
        return false;
      }
    }
    return true;

  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
  }
}

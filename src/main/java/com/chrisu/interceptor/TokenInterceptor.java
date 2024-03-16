package com.chrisu.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chrisu.controller.Result;
import com.chrisu.utils.TokenUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)throws Exception{
    if(request.getMethod().equals("OPTIONS")){
      response.setStatus(HttpServletResponse.SC_OK);
      return true;
    }
    response.setCharacterEncoding("utf-8");
    String token = request.getHeader("token"); //前端vue将token添加在请求头中
    if(token != null){
      boolean result = TokenUtil.verify(token);
      if(result){
//        System.out.println("通过拦截器");
        return true;
      }
    }
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json; charset=utf-8");
    try{
      Result result = Result.error("请先登录!");
      response.getWriter().append(JSON.toJSONString(result));
//      System.out.println("认证失败，未通过拦截器");

    }catch (Exception e){
      e.printStackTrace();
      response.sendError(500);
      return false;
    }
    return false;
  }
}

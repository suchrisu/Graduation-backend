package com.chrisu.config;


import com.chrisu.interceptor.LoginInterceptor;
import com.chrisu.interceptor.TokenInterceptor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * web配置类
 */
@Configuration
public class WebConfigure extends WebMvcConfigurationSupport {

  @Autowired
  LoginInterceptor loginInterceptor;
  @Autowired
  TokenInterceptor tokenInterceptor;

  /**
   * 注册拦截器
   *
   * @param registry
   */
  @Override
  protected void addInterceptors(InterceptorRegistry registry) {
    List<String> excludePath = new ArrayList<>();
    //排除拦截，除了注册登录(此时还没token)，其他都拦截
    excludePath.add("/login/**");  //登录
    excludePath.add("/register/**");     //注册
    excludePath.add("/userFile/**");
    excludePath.add("/sse/**");

    registry.addInterceptor(tokenInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns(excludePath);
    WebConfigure.super.addInterceptors(registry);

  }
}

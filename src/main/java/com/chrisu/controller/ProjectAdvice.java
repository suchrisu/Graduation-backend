package com.chrisu.controller;

import com.alibaba.fastjson.JSONException;
import com.chrisu.POJO.ChatMessage;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 处理异常
 */
@RestControllerAdvice
public class ProjectAdvice {


  @ExceptionHandler(JSONException.class)
  public Result jsonExceptionHandler(JSONException jsonException){
    return Result.success(new ArrayList<ChatMessage>());
  }
  @ExceptionHandler(Exception.class)
  public Result exceptionHandler(Exception exception) {
    System.out.println(new Timestamp(System.currentTimeMillis()));
    exception.printStackTrace();
    System.out.println("-----------------------------------------------------------------------");
    System.out.println("-----------------------------------------------------------------------");
    return Result.error("系统繁忙,请稍后重试!");
  }


}

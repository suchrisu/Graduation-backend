package com.chrisu.controller;


import com.chrisu.service.UserFileService;
import java.io.*;
import java.util.UUID;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 处理用户上传和获取头像的controller
 */
@RestController
@RequestMapping("/userFile")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class UserFileController {


  @Autowired
  UserFileService userFileService;


  @GetMapping("/getHeader")
  public void getHeader(HttpServletRequest request,HttpServletResponse response){
    String userHeader = request.getParameter("userHeader");
    userFileService.getHeader(request,response);

  }

  @PostMapping("/uploadHeader")
  public Result uploadHeader(HttpServletRequest request,@RequestParam("file") MultipartFile file){
    return userFileService.updateHeader(request,file);
  }

}

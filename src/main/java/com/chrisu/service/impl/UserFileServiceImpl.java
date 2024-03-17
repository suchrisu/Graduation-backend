package com.chrisu.service.impl;

import com.alibaba.fastjson.JSON;
import com.chrisu.POJO.User;
import com.chrisu.controller.CurrentUserUtil;
import com.chrisu.controller.Result;
import com.chrisu.mapper.UserMapper;
import com.chrisu.service.UserFileService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserFileServiceImpl implements UserFileService {

  @Value("${PolicyLLM.userFileContext.headerFileContext}")
  private String headerContext;

  @Autowired private UserMapper userMapper;

  /**
   * 处理用户上传文件请求
   *
   * @param file 上传的文件
   * @return
   * @throws IOException
   */
  public boolean upload(MultipartFile file, String filePath) {

    try {
      file.transferTo(new File(filePath));
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * 处理用户获取文件的请求
   *
   * @param response
   * @param fileName 用户要获取的文件的名称（不带目录）
   * @return
   * @throws IOException
   */
  public void download(HttpServletResponse response, String fileName) throws IOException {
    response.setContentType("image/*");
    FileInputStream fileInputStream = new FileInputStream(fileName);
    ServletOutputStream responseOutputStream = response.getOutputStream();
    IOUtils.copy(fileInputStream, responseOutputStream);
    fileInputStream.close();
    responseOutputStream.close();
  }

  @Override
  public void getHeader(HttpServletRequest request, HttpServletResponse response) {
    String userHeader = request.getParameter("userHeader");
    String userHeaderFilePath =
        "defaultHeader.png".equals(userHeader)
            ? headerContext + "/" + userHeader
            : headerContext
                + "/"
                + CurrentUserUtil.getCurrentUser(request.getSession()).getUserId()
                + "/"
                + userHeader;
    try {
      System.out.println(userHeaderFilePath);
      download(response, userHeaderFilePath);
    } catch (IOException e) {
      response.setContentType("application/json; charset=utf-8");
      try {
        response.getWriter().append(JSON.toJSONString(Result.error("用户头像加载失败,请稍后重试!")));
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  @Override
  public Result updateHeader(HttpServletRequest request, MultipartFile file) {
    HttpSession session = request.getSession();
    User currentUser = CurrentUserUtil.getCurrentUser(session);
    String context = headerContext + "/" + currentUser.getUserId();
    File dir = new File(context);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    String originalFilename = file.getOriginalFilename();
    String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
    String fileName = UUID.randomUUID() + suffix;
    if (upload(file, context + "/" + fileName)) {
      currentUser.setuserHeader(fileName);
      userMapper.update(currentUser);
      CurrentUserUtil.setCurrentUser(session, currentUser);
      return Result.success(fileName);
    }
    return Result.error("上传失败,请稍后重试!");
  }
}

package com.chrisu.service;

import com.chrisu.controller.Result;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
public interface UserFileService {

  void getHeader(HttpServletRequest request,HttpServletResponse response);

  Result updateHeader(HttpServletRequest request, MultipartFile file);
}

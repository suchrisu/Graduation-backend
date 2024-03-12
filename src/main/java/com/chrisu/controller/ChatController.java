package com.chrisu.controller;

import com.chrisu.POJO.ChatMessage;
import com.chrisu.service.ChatMessageService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class ChatController {
  @Value("${PolicyLLM.sessionFileContext}")
  String sessionFileContext;

  @Autowired ChatMessageService chatMessageService;

  @PostMapping("/chatWithLLM")
  Result chatLLM(@RequestBody ChatMessage chatMessage, HttpServletRequest request) {
    String context =sessionFileContext
        + "/"
        + ControllerUtil.getCurrentUser(request.getSession()).getUserId();
    String sessionFile = request.getParameter("sessionFile");
    return chatMessageService.sendChatMessage(chatMessage, context,sessionFile);
  }

  @GetMapping("/getChatMessageList")
  Result getChatMessageList(HttpServletRequest request) {
    String context =sessionFileContext
        + "/"
        + ControllerUtil.getCurrentUser(request.getSession()).getUserId();
    String sessionFile = request.getParameter("sessionFile");
    return chatMessageService.getChatMessage(context,sessionFile);
  }
}

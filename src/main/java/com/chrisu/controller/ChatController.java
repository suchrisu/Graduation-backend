package com.chrisu.controller;

import com.chrisu.POJO.ChatMessage;
import com.chrisu.service.ChatMessageService;
import com.chrisu.service.SseEmitterService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/chat")
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class ChatController {
  @Value("${PolicyLLM.sessionFileContext}")
  String sessionFileContext;

  @Autowired
  SseEmitterService sseEmitterService;

  @Autowired ChatMessageService chatMessageService;

  @PostMapping("/chatWithLLM")
  Result chatLLM(@RequestBody ChatMessage chatMessage, HttpServletRequest request) {
    String context =sessionFileContext
        + "/"
        + CurrentUserUtil.getCurrentUser(request.getSession()).getUserId();
    String sessionFile = request.getParameter("sessionFile");
    return chatMessageService.sendChatMessage(chatMessage, context,sessionFile);
  }

  @GetMapping("/getChatMessageList")
  Result getChatMessageList(HttpServletRequest request) {
    String context =sessionFileContext
        + "/"
        + CurrentUserUtil.getCurrentUser(request.getSession()).getUserId();
    String sessionFile = request.getParameter("sessionFile");
    return chatMessageService.getChatMessage(context,sessionFile);
  }

  @GetMapping(value = "/createSseConnect",produces = "text/event-stream;charset=UTF-8")
  public SseEmitter createSseConnect(HttpServletRequest request){
    String clientId = request.getParameter("clientId");
    return sseEmitterService.createConnect(clientId);
  }

  @GetMapping("/closeSseConnect")
  public Result closeSseConnect(HttpServletRequest request){
    String clientId = request.getParameter("clientId");
    sseEmitterService.closeConnect(clientId);
    return Result.success(true);
  }

  @GetMapping("/eval")
  public Result eval(HttpServletRequest request){
    String context =sessionFileContext
        + "/"
        + CurrentUserUtil.getCurrentUser(request.getSession()).getUserId();
    String sessionFile = request.getParameter("sessionFile");
    String id = request.getParameter("id");
    boolean like = Boolean.parseBoolean(request.getParameter("like"));
    return chatMessageService.eval(context,sessionFile,id,like);
  }




}

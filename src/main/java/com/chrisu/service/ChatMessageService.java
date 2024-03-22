package com.chrisu.service;

import com.chrisu.POJO.ChatMessage;
import com.chrisu.controller.Result;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;


public interface ChatMessageService {
  Result sendChatMessage(ChatMessage chatMessage, String context,String sesisonFile);

  Result getChatMessage(String context,String sessionFile);

  String sendToLLM(ChatMessage userMessage, List<ChatMessage> history,String clientId);

  Result eval(String context,String sessionFile, String id,boolean like);


}

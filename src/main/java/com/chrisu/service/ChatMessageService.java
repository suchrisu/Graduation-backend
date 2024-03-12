package com.chrisu.service;

import com.chrisu.POJO.ChatMessage;
import com.chrisu.controller.Result;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ChatMessageService {
  Result sendChatMessage(ChatMessage chatMessage, String context,String sesisonFile);

  Result getChatMessage(String context,String sessionFile);
}

package com.chrisu.POJO;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ChatMessage {
  public static int  TEXT = 0;
  public static int IMG = 1;
  public static int FILE = 2;
  private String role;
  private String content;
  private int chatType;

  public static ChatMessage assistantChatMessage(String content){
    return new ChatMessage("assistant",content,TEXT);
  }

  public static ChatMessage userChatMessage(String content){
    return new ChatMessage("user",content,TEXT);
  }

  public ChatMessage() {
  }

  public ChatMessage(String role, String content, int chatType) {
    this.role = role;
    this.content = content;
    this.chatType = chatType;
  }


  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getChatType() {
    return chatType;
  }

  public void setChatType(int chatType) {
    this.chatType = chatType;
  }

  @Override
  public String toString() {
    return "ChatMessage{" +
        "role='" + role + '\'' +
        ", content='" + content + '\'' +
        ", chatType=" + chatType +
        '}';
  }
}

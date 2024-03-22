package com.chrisu.POJO;

import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class ChatMessage {
  public static int  TEXT = 0;
  public static int IMG = 1;
  public static int FILE = 2;
  public static int LIKE = 1;
  public static int DEFAULT = 0;
  public static int UNLIKE = -1;
  private String id;
  private String role;
  private String content;
  private int chatType;
  private int useful;

  public static ChatMessage assistantChatMessage(String content){
    return new ChatMessage(UUID.randomUUID().toString(),"assistant",content,TEXT,DEFAULT);
  }

  public static ChatMessage userChatMessage(String content){
    return new ChatMessage("","user",content,TEXT,DEFAULT);
  }

  public ChatMessage() {
  }

  public ChatMessage(String id, String role, String content, int chatType, int useful) {
    this.id = id;
    this.role = role;
    this.content = content;
    this.chatType = chatType;
    this.useful = useful;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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

  public int getUseful() {
    return useful;
  }

  public void setUseful(int useful) {
    this.useful = useful;
  }

  @Override
  public String toString() {
    return "ChatMessage{" +
        "id='" + id + '\'' +
        ", role='" + role + '\'' +
        ", content='" + content + '\'' +
        ", chatType=" + chatType +
        ", useful=" + useful +
        '}';
  }
}

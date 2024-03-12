package com.chrisu.service.impl;

import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.JSONWriter;
import com.chrisu.POJO.ChatMessage;
import com.chrisu.controller.Result;
import com.chrisu.service.ChatMessageService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

  public List<ChatMessage> readChatMessageFromFile(String context ,String file){
    List<ChatMessage> chatMessageList = new ArrayList<>();
    try {
      File contextTemp = new File(context);
      if(!contextTemp.exists()){
        return new ArrayList<ChatMessage>();
      }
      File fileTemp = new File(context+"/"+file);
      if(!fileTemp.exists() || fileTemp.length()==0){
        return new ArrayList<ChatMessage>();
      }
      JSONReader jsonReader = new JSONReader(new FileReader(fileTemp));
      jsonReader.startArray();
      while(jsonReader.hasNext()){
        chatMessageList.add(jsonReader.readObject(ChatMessage.class));
      }
      jsonReader.endArray();
      jsonReader.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return chatMessageList;
  }
  public boolean writeChatMessageToFile(List<ChatMessage> chatMessageList,String context,String file){
    try {
      File contextTemp = new File(context);
      if(!contextTemp.exists()){
        contextTemp.mkdirs();
      }
      File fileTemp = new File(context+"/"+file);
      if(!fileTemp.exists()){
        fileTemp.createNewFile();
      }
      JSONWriter jsonWriter = new JSONWriter(new FileWriter(fileTemp));
      jsonWriter.startArray();
      for(ChatMessage chatMessage:chatMessageList){
        jsonWriter.writeObject(chatMessage);
      }
      jsonWriter.endArray();
      jsonWriter.close();
      return true;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public Result sendChatMessage(ChatMessage chatMessage, String context,String sessionFile) {
    ChatMessage assistantMessage = ChatMessage.assistantChatMessage("这是一条回答消息。。。");
    List<ChatMessage> chatMessageList = readChatMessageFromFile(context,sessionFile);
    chatMessageList.add(chatMessage);
    chatMessageList.add(assistantMessage);
    if(writeChatMessageToFile(chatMessageList,context,sessionFile)){
      return Result.success(chatMessageList);
    }
    return Result.error("发送失败!");
  }

  @Override
  public Result getChatMessage(String context ,String sessionFile) {
    return Result.success(readChatMessageFromFile(context,sessionFile));
  }
}

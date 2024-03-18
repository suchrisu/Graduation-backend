package com.chrisu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.JSONWriter;
import com.chrisu.POJO.ChatBody;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

  private static String CHAT_WITH_LLM_URL = "http://localhost:7861/chat/chat";

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
    List<ChatMessage> chatMessageList = readChatMessageFromFile(context,sessionFile);
    String llmMessage = sendToLLM(chatMessage,chatMessageList);
    ChatMessage assistantMessage = ChatMessage.assistantChatMessage(llmMessage);
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

  @Override
  public String sendToLLM(ChatMessage userMessage,List<ChatMessage> history){
    RestTemplate restTemplate = new RestTemplate();
//    HttpHeaders headers = new HttpHeaders();
//    MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//    headers.setContentType(type);
//    headers.add("Accept", MediaType.APPLICATION_JSON.toString());
    ChatBody chatBody = ChatBody.getCommonChatBody(userMessage.getContent(),history);
    JSONObject json = (JSONObject) JSON.toJSON(chatBody);
    json.remove("modelName");
    json.put("model_name",chatBody.getModelName());
    System.out.println(json);
    try{
      ResponseEntity<String> responseEntity = restTemplate.postForEntity(CHAT_WITH_LLM_URL, json, String.class);
      return responseEntity.getBody();
    }catch (Exception e){

    }
    return "大模型掉线了。。。";
  }
}

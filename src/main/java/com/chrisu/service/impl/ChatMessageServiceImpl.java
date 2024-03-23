package com.chrisu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.JSONWriter;
import com.chrisu.POJO.ChatBody;
import com.chrisu.POJO.ChatMessage;
import com.chrisu.controller.Result;
import com.chrisu.service.ChatMessageService;
import com.chrisu.service.SseEmitterService;
import com.chrisu.utils.SSEUtil;
import com.chrisu.utils.TimeStampUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import jdk.nashorn.internal.ir.WhileNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

  private static String CHAT_WITH_LLM_URL = "http://localhost:7861/chat/chat";
  @Autowired SseEmitterService sseEmitterService;

  public List<ChatMessage> readChatMessageFromFile(String context, String file, String id,boolean useful) {
    List<ChatMessage> chatMessageList = new ArrayList<>();
    try {
      File contextTemp = new File(context);
      if (!contextTemp.exists()) {
        return new ArrayList<ChatMessage>();
      }
      File fileTemp = new File(context + "/" + file);
      if (!fileTemp.exists() || fileTemp.length() == 0) {
        return new ArrayList<ChatMessage>();
      }
      JSONReader jsonReader = new JSONReader(new FileReader(fileTemp));
      jsonReader.startArray();
      while (jsonReader.hasNext()) {
        ChatMessage chatMessage = jsonReader.readObject(ChatMessage.class);
        if(id != null && id.equals(chatMessage.getId())){
          chatMessage.setUseful(useful? 1 : -1);
        }
        chatMessageList.add(chatMessage);
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

  public boolean writeChatMessageToFile(
      List<ChatMessage> chatMessageList, String context, String file) {
    try {
      File contextTemp = new File(context);
      if (!contextTemp.exists()) {
        contextTemp.mkdirs();
      }
      File fileTemp = new File(context + "/" + file);
      if (!fileTemp.exists()) {
        fileTemp.createNewFile();
      }
      JSONWriter jsonWriter = new JSONWriter(new FileWriter(fileTemp));
      jsonWriter.startArray();
      for (ChatMessage chatMessage : chatMessageList) {
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
  public Result sendChatMessage(ChatMessage chatMessage, String context, String sessionFile) {
    List<ChatMessage> historyChatMessageList = readChatMessageFromFile(context, sessionFile,null,false);
    String llmMessage = sendToLLM(chatMessage, historyChatMessageList, sessionFile);
    ChatMessage assistantMessage = ChatMessage.assistantChatMessage(llmMessage);
    historyChatMessageList.add(chatMessage);
    historyChatMessageList.add(assistantMessage);
    if (writeChatMessageToFile(historyChatMessageList, context, sessionFile)) {
      return Result.success(historyChatMessageList);
    }
    return Result.error("发送失败!");
  }

  @Override
  public Result getChatMessage(String context, String sessionFile) {
    return Result.success(readChatMessageFromFile(context, sessionFile,null,false));
  }

  @Override
  public String sendToLLM(ChatMessage userMessage, List<ChatMessage> history, String clientId) {
    ChatBody chatBody = ChatBody.getCommonChatBody(userMessage.getContent(), history);
    JSONObject requestBody = (JSONObject) JSON.toJSON(chatBody);
    requestBody.remove("modelName");
    StringBuffer llmMessage = new StringBuffer();
//    测试
    for (int i = 0; i < 30; i++) {
      try {
        Thread.sleep(100);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      Result result = Result.success("这是一条消息。。。。。。。。。。。。。。。。。。。。。。。。。。。。"+i+"\n");
      try{
        sseEmitterService.sendMessageToOneClient(clientId, result);
      }catch(RuntimeException exception){
        if("sseEmitter为空".equals(exception.getMessage())){
          return llmMessage.toString();
        }
      }

      llmMessage.append(result.getData());
    }
    sseEmitterService.closeConnect(clientId);
    return llmMessage.toString();
//    try {
//      InputStream is = SSEUtil.getSseInputStream(CHAT_WITH_LLM_URL, requestBody.toJSONString());
//      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
//      String line = "";
//      while ((line = bufferedReader.readLine()) != null) {
//        System.out.println(line);
//        line = line.substring(line.indexOf("{"));
//        JSONObject resultJsonObject = JSONObject.parseObject(line);
//        String text = resultJsonObject.getString("text");
//        llmMessage.append(text);
//        System.out.println(text);
//        Result result = Result.success(text);
//        try{
//        sseEmitterService.sendMessageToOneClient(clientId, result);
//        } catch (RuntimeException exception) {
//          if ("sseEmitter为空".equals(exception.getMessage())) {
//            return llmMessage.toString();
//          }
//      }
//        bufferedReader.readLine();
//      }
//      //         当服务器端主动关闭的时候，客户端无法获取到信号。现在还不清楚原因。所以无法执行的此处。
//      is.close();
//      bufferedReader.close();
//      sseEmitterService.closeConnect(clientId);
//      return llmMessage.toString();
//
//    } catch (Exception e) {
//      sseEmitterService.closeConnect(clientId);
//      throw new RuntimeException("大模型掉线了。。。");
//    }

  }

  @Override
  public Result eval(String context,String sessionFile, String id,boolean like) {
    List<ChatMessage> chatMessageList = readChatMessageFromFile(context,sessionFile,id,like);
    writeChatMessageToFile(chatMessageList,context,sessionFile);
    return Result.success(chatMessageList);
  }

}

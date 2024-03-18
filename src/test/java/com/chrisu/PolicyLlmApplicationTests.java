package com.chrisu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONReader;
import com.alibaba.fastjson.JSONWriter;
import com.chrisu.POJO.ChatMessage;
import com.chrisu.service.ChatMessageService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PolicyLlmApplicationTests {
  @Autowired
  ChatMessageService chatMessageService;

  @Test
  void contextLoads() {
  }

  @Test
  void testChatMessageToJson(){
    ChatMessage chatMessage1 = ChatMessage.userChatMessage("哈哈哈");
    ChatMessage chatMessage2 = ChatMessage.assistantChatMessage("123");
    ChatMessage chatMessage3 = ChatMessage.assistantChatMessage("你好");
    ChatMessage chatMessage4 = ChatMessage.assistantChatMessage("好的");

    List<ChatMessage> chatMessageList = new ArrayList<>();
    chatMessageList.add(chatMessage1);
    chatMessageList.add(chatMessage2);
    chatMessageList.add(chatMessage3);
    chatMessageList.add(chatMessage4);

    String jsonArray = JSONArray.toJSONString(chatMessageList);
    System.out.println(jsonArray);
    String json = "[{\"chatType\":0,\"content\":\"哈哈哈\",\"role\":\"user\"},{\"chatType\":0,\"content\":\"123\",\"role\":\"assistant\"},{\"chatType\":0,\"content\":\"你好\",\"role\":\"assistant\"},{\"chatType\":0,\"content\":\"好的\",\"role\":\"assistant\"}]";
    List<ChatMessage> chatMessageList1 = JSONArray.parseArray(json,ChatMessage.class);
    System.out.println(chatMessageList1);
  }

  @Test
  void getPath(){

    File appBase = new File("."); //current directory
    String path = appBase.getAbsolutePath();
    System.out.println(path);
  }

  @Test
  void testJsonReader() throws FileNotFoundException {
    List<ChatMessage> list = new ArrayList<>();
    JSONReader reader = new JSONReader(new FileReader("./src/test1.json"));
    reader.startObject();
    while (reader.hasNext()) {
          ChatMessage chatMessage = reader.readObject(ChatMessage.class);
//        String key = reader.readString();
//        if ("chatType".equals(key)) {
//          chatMessage.setChatType(Integer.parseInt(reader.readString()));
//        } else if ("role".equals(key)) {
//          chatMessage.setRole(reader.readString());
//        } else if ("content".equals(key)) {
//          chatMessage.setContent(reader.readString());
//        } else {
//          reader.readObject();//读取对象
//        }
        list.add(chatMessage);

    }
    reader.endObject();
    System.out.println(list);
    reader.close(); //关闭流

  }

  @Test
  void testJsonWriter() throws IOException {
    JSONWriter jsonWriter = new JSONWriter(new FileWriter("./src/test3.json"));
    ChatMessage chatMessage1 = ChatMessage.assistantChatMessage("hahaha");
    ChatMessage chatMessage2 = ChatMessage.userChatMessage("666");
    List<ChatMessage> chatMessageList = new ArrayList<>();
    chatMessageList.add(chatMessage1);
    chatMessageList.add(chatMessage2);
    int i = 0;
    for (ChatMessage chatMessage:chatMessageList){
      jsonWriter.writeKey(String.valueOf(i));
      jsonWriter.writeValue(chatMessage);
      System.out.println(chatMessage);
      i++;
    }
    jsonWriter.close();
  }

  @Test
  void testSendToLLM(){
    ChatMessage chatMessage2 = ChatMessage.userChatMessage("你好");
    chatMessageService.sendToLLM(chatMessage2, new ArrayList<ChatMessage>());
  }

}

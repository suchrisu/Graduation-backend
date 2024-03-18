package com.chrisu.POJO;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ChatBody {
  private String query;
  private List<ChatMessage> history;
  private boolean stream;
  private String modelName;
  private double temperature;
  private int maxTokens;
  private String promptName;

  public ChatBody() {
  }

  public ChatBody(String query, List<ChatMessage> history, boolean stream, String modelName,
      double temperature, int maxTokens, String promptName) {
    this.query = query;
    this.history = history;
    this.stream = stream;
    this.modelName = modelName;
    this.temperature = temperature;
    this.maxTokens = maxTokens;
    this.promptName = promptName;
  }

  public static ChatBody getCommonChatBody(String query,List<ChatMessage> history){
    boolean stream = false;
    String modelName = "chatglm2-6b";
    double temperature = 0.7;
    int maxTokens = 1024;
    return new ChatBody(query,history,stream,modelName,temperature,maxTokens,null);

  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public List<ChatMessage> getHistory() {
    return history;
  }

  public void setHistory(List<ChatMessage> history) {
    this.history = history;
  }

  public boolean isStream() {
    return stream;
  }

  public void setStream(boolean stream) {
    this.stream = stream;
  }

  public String getModelName() {
    return modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  public double getTemperature() {
    return temperature;
  }

  public void setTemperature(double temperature) {
    this.temperature = temperature;
  }

  public int getMaxTokens() {
    return maxTokens;
  }

  public void setMaxTokens(int maxTokens) {
    this.maxTokens = maxTokens;
  }

  public String getPromptName() {
    return promptName;
  }

  public void setPromptName(String promptName) {
    this.promptName = promptName;
  }

  @Override
  public String toString() {
    return "ChatBody{" +
        "query='" + query + '\'' +
        ", history=" + history +
        ", stream=" + stream +
        ", modelName='" + modelName + '\'' +
        ", temperature=" + temperature +
        ", maxTokens=" + maxTokens +
        ", promptName='" + promptName + '\'' +
        '}';
  }
}

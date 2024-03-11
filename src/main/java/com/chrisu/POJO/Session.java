package com.chrisu.POJO;

import org.springframework.stereotype.Component;

@Component
public class Session {
  private String sessionId;
  private String sessionOwner;
  private String sessionName;
  private String sessionFile;
  private String sessionLastTime;


  public Session() {
  }

  public Session(String sessionId, String sessionOwner, String sessionName, String sessionFile,
      String sessionLastTime) {
    this.sessionId = sessionId;
    this.sessionOwner = sessionOwner;
    this.sessionName = sessionName;
    this.sessionFile = sessionFile;
    this.sessionLastTime = sessionLastTime;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getSessionOwner() {
    return sessionOwner;
  }

  public void setSessionOwner(String sessionOwner) {
    this.sessionOwner = sessionOwner;
  }

  public String getSessionName() {
    return sessionName;
  }

  public void setSessionName(String sessionName) {
    this.sessionName = sessionName;
  }

  public String getSessionFile() {
    return sessionFile;
  }

  public void setSessionFile(String sessionFile) {
    this.sessionFile = sessionFile;
  }

  public String getSessionLastTime() {
    return sessionLastTime;
  }

  public void setSessionLastTime(String sessionLastTime) {
    this.sessionLastTime = sessionLastTime;
  }

  @Override
  public String toString() {
    return "Session{" +
        "sessionId='" + sessionId + '\'' +
        ", sessionOwner='" + sessionOwner + '\'' +
        ", sessionName='" + sessionName + '\'' +
        ", sessionFile='" + sessionFile + '\'' +
        ", sessionLastTime='" + sessionLastTime + '\'' +
        '}';
  }
}

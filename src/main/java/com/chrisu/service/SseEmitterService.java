package com.chrisu.service;

import com.chrisu.controller.Result;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Transactional
public interface SseEmitterService {
  /**
   * 创建连接
   *
   * @param clientId 客户端ID
   */
  SseEmitter createConnect(String clientId);

  /**
   * 根据客户端id获取SseEmitter对象
   *
   * @param clientId 客户端ID
   */
  SseEmitter getSseEmitterByClientId(String clientId);

  /**
   * 发送消息给所有客户端
   *
   */
  void sendMessageToAllClient(Result result);

  /**
   * 给指定客户端发送消息
   *
   * @param clientId 客户端ID
   */
  void sendMessageToOneClient(String clientId, Result result);

  /**
   * 关闭连接
   *
   * @param clientId 客户端ID
   */
  void closeConnect(String clientId);
}

package com.chrisu.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.chrisu.controller.Result;
import com.chrisu.service.SseEmitterService;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Service
@Slf4j
public class SseEmitterServiceImpl implements SseEmitterService {
  /**
   * 容器，保存连接，用于输出返回 ;可使用其他方法实现
   */
  private static final Map<String, SseEmitter> sseCache = new ConcurrentHashMap<>();


  /**
   * 根据客户端id获取SseEmitter对象
   *
   * @param clientId 客户端ID
   */
  @Override
  public SseEmitter getSseEmitterByClientId(String clientId) {
    return sseCache.get(clientId);
  }


  /**
   * 创建连接
   *
   * @param clientId 客户端ID
   */
  @Override
  public SseEmitter createConnect(String clientId) {
    // 设置超时时间，0表示不过期。默认30秒，超过时间未完成会抛出异常：AsyncRequestTimeoutException
    SseEmitter sseEmitter = new SseEmitter(0L);
    // 是否需要给客户端推送ID
    if (StrUtil.isBlank(clientId)) {
      clientId = IdUtil.simpleUUID();
    }
    // 注册回调
    sseEmitter.onCompletion(completionCallBack(clientId));     // 长链接完成后回调接口(即关闭连接时调用)
    sseEmitter.onTimeout(timeoutCallBack(clientId));        // 连接超时回调
    sseEmitter.onError(errorCallBack(clientId));          // 推送消息异常时，回调方法
    sseCache.put(clientId, sseEmitter);
    log.info("创建新的sse连接，当前用户：{}    累计用户:{}", clientId, sseCache.size());
    try {
      // 注册成功返回用户信息
      sseEmitter.send(SseEmitter.event().id(String.valueOf(HttpStatus.HTTP_CREATED)).data("[CONNECT]", MediaType.TEXT_PLAIN));
    } catch (IOException e) {
      log.error("创建长链接异常，客户端ID:{}   异常信息:{}", clientId, e.getMessage());
    }
    return sseEmitter;
  }

  /**
   * 发送消息给所有客户端
   *
   */
  @Override
  public void sendMessageToAllClient(Result result) {
    if (MapUtil.isEmpty(sseCache)) {
      return;
    }
    // 判断发送的消息是否为空

    for (Map.Entry<String, SseEmitter> entry : sseCache.entrySet()) {

      sendMsgToClientByClientId(entry.getKey(), entry.getValue(),result);
    }

  }

  /**
   * 给指定客户端发送消息
   *
   * @param clientId 客户端ID
   */
  @Override
  public void sendMessageToOneClient(String clientId, Result result) {

    sendMsgToClientByClientId(clientId, sseCache.get(clientId),result);
  }

  /**
   * 关闭连接
   *
   * @param clientId 客户端ID
   */
  @Override
  public void closeConnect(String clientId) {
    SseEmitter sseEmitter = sseCache.get(clientId);
    try {
      sseEmitter.send(SseEmitter.event().id(String.valueOf(HttpStatus.HTTP_OK)).data("[DONE]", MediaType.TEXT_PLAIN));
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (sseEmitter != null) {
      sseEmitter.complete();
//      removeUser(clientId);
    }
  }

  /**
   * 推送消息到客户端
   * 此处做了推送失败后，重试推送机制，可根据自己业务进行修改
   *
   * @param clientId  客户端ID
   **/
  private void sendMsgToClientByClientId(String clientId, SseEmitter sseEmitter,Result result) {
    if (sseEmitter == null) {
//      "推送消息失败：客户端{}未创建长链接,失败消息:{}"
      throw new RuntimeException("sseEmitter为空");
    }
    SseEmitter.SseEventBuilder sendData = SseEmitter.event().id(String.valueOf(HttpStatus.HTTP_OK))
        .data(result, MediaType.APPLICATION_JSON);
    try {
      sseEmitter.send(sendData);
    } catch (IOException e) {
      System.out.println("推送失败");
      // 推送消息失败，记录错误日志，进行重推
      boolean isSuccess = true;
      // 推送消息失败后，每隔10s推送一次，推送5次
      for (int i = 0; i < 5; i++) {
        try {
          Thread.sleep(10000);
          sseEmitter = sseCache.get(clientId);
          if (sseEmitter == null) {
            log.error("{}的第{}次消息重推失败，未创建长链接", clientId, i + 1);
            continue;
          }
          sseEmitter.send(sendData);
        } catch (Exception ex) {
          log.error("{}的第{}次消息重推失败", clientId, i + 1, ex);
          continue;
        }
        return;
      }
    }
  }


  /**
   * 长链接完成后回调接口(即关闭连接时调用)
   *
   * @param clientId 客户端ID
   **/
  private Runnable completionCallBack(String clientId) {
    return () -> {
      log.info("结束连接：{}", clientId);
      removeUser(clientId);
    };
  }

  /**
   * 连接超时时调用
   *
   * @param clientId 客户端ID
   **/
  private Runnable timeoutCallBack(String clientId) {
    return () -> {
      log.info("连接超时：{}", clientId);
      removeUser(clientId);
    };
  }

  /**
   * 推送消息异常时，回调方法
   *
   * @param clientId 客户端ID
   *
   * @return*/
  private Consumer<Throwable> errorCallBack(String clientId) {
    return throwable -> {
      log.error("SseEmitterServiceImpl[errorCallBack]：连接异常,客户端ID:{}", clientId);

      // 推送消息失败后，每隔10s推送一次，推送5次
      for (int i = 0; i < 5; i++) {
        try {
          Thread.sleep(10000);
          SseEmitter sseEmitter = sseCache.get(clientId);
          if (sseEmitter == null) {
            log.error("SseEmitterServiceImpl[errorCallBack]：第{}次消息重推失败,未获取到 {} 对应的长链接", i + 1, clientId);
            continue;
          }
          sseEmitter.send("失败后重新推送");
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    };
  }

  /**
   * 移除用户连接
   *
   * @param clientId 客户端ID
   **/
  private void removeUser(String clientId) {
    sseCache.remove(clientId);
    log.info("SseEmitterServiceImpl[removeUser]:移除用户：{}", clientId);
  }
}
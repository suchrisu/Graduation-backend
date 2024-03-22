//package com.chrisu.controller;
//
//import com.chrisu.service.SseEmitterService;
//import javax.annotation.Resource;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
//
//@RestController
//@RequestMapping("/sse")
//public class SseEmitterController {
//
//  @Resource
//  private SseEmitterService sseEmitterService;
//
//
//  @CrossOrigin
//  @GetMapping("/createConnect")
//  public SseEmitter createConnect(String clientId) {
//    return sseEmitterService.createConnect(clientId);
//  }
////
////  @CrossOrigin
////  @PostMapping("/broadcast")
////  public void sendMessageToAllClient(@RequestBody(required = false) String msg) {
////    sseEmitterService.sendMessageToAllClient(msg);
////  }
////
////  @CrossOrigin
////  @PostMapping("/sendMessage")
////  public void sendMessageToOneClient(@RequestBody(required = false) MessageVo messageVo) {
////    if (messageVo.getClientId().isEmpty()) {
////      return;
////    }
////    sseEmitterService.sendMessageToOneClient(messageVo.getClientId(), messageVo.getData());
////  }
////
////  @CrossOrigin
////  @GetMapping("/closeConnect")
////  public void closeConnect(@RequestParam(required = true) String clientId) {
////    sseEmitterService.closeConnect(clientId);
////  }
//
//}
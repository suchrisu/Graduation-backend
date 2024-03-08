package com.chrisu.controller;

/**
 * 返回给前端的统一结果类
 */
public class Result {

  private Integer code; // 返回结果码 1为成功 0为失败
  private Object data; // 返回的数据，当失败时为null
  private String message; // 返回的错误信息，当成功时为空字符串

  public Result() {
  }

  public Result(Integer code, Object data, String message) {
    this.code = code;
    this.data = data;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return "Result{" +
        "code=" + code +
        ", data=" + data +
        ", message='" + message + '\'' +
        '}';
  }

  /**
   * 返回成功结果的方法
   *
   * @param data 成功结果对应的数据
   * @return
   */
  public static Result success(Object data) {
    return new Result(1, data, "");
  }

  /**
   * 返回失败结果的方法
   *
   * @param message 失败结果对应的失败消息
   * @return
   */
  public static Result error(String message) {
    return new Result(0, null, message);
  }
}

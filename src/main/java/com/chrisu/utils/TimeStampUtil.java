package com.chrisu.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 获取当前时间戳工具类
 */
public class TimeStampUtil {

  /**
   * 获取当前时间戳
   *
   * @return
   */
  public static String getTimeStamp() {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String loginTime = dateFormat.format(timestamp);
    return loginTime;
  }
}

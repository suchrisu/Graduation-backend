package com.chrisu.utils;

import java.util.Random;

/**
 * 获取6位随机验证码工具类
 */
public class RandomCode {

  /**
   * 获取6位随机验证码
   *
   * @return
   */
  public static String getRandomCode() {
    Random random = new Random();
    String registerCode = new String();
    for (int i = 0; i < 6; i++) {
      registerCode = registerCode + random.nextInt(10);
    }
    return registerCode;
  }
}

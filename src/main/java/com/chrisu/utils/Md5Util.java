package com.chrisu.utils;

import java.nio.charset.StandardCharsets;
import org.springframework.util.DigestUtils;

public class Md5Util {
  private static String MD5_SALT = "chrisu";
  public static String md5(String input){
    input = input + MD5_SALT;
    return DigestUtils.md5DigestAsHex(input.getBytes(StandardCharsets.UTF_8));
  }
}

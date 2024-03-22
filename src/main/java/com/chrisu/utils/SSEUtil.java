package com.chrisu.utils;



import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class SSEUtil {

  /**
   * 获取SSE输入流。
   *
   * @param urlPath
   * @return
   * @throws IOException
   */
  public static InputStream getSseInputStream(String urlPath,String jsonString) throws IOException {
    URL url = new URL(urlPath);
    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    urlConnection.setConnectTimeout(3*1000);
    urlConnection.setReadTimeout(60 * 1000);
    urlConnection.setDoOutput(true);
    urlConnection.setDoInput(true);
    urlConnection.setUseCaches(false);
    urlConnection.setRequestMethod("POST");
    urlConnection.setRequestProperty("Connection", "Keep-Alive");
    urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
    byte[] bytes = jsonString.getBytes();
    DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
    dos.write(bytes);
    dos.flush();
    dos.close();
    InputStream inputStream = urlConnection.getInputStream();
    InputStream is = new BufferedInputStream(inputStream);
    return is;
  }


  //TODO 可用method





}

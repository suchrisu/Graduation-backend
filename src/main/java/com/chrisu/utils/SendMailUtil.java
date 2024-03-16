package com.chrisu.utils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.stereotype.Component;

/**
 * 发送邮件类，用来给用户的邮箱发送注册码
 */
@Component
public class SendMailUtil {

  public static final String FROM_MAIL = "1374033672@qq.com";
  public static final String FROM_MAIL_PASSWORD = "rdtxktunacotigef";

  /**
   * 发送邮件(参数自己根据自己的需求来修改，发送短信验证码可以直接套用这个模板)
   *
   * @param from_email 发送人的邮箱
   * @param pwd        发送人的授权码
   * @param recevices  接收人的邮箱
   * @return 发送成功返回true, 发送失败返回false
   */
  public static boolean sendEmail(String from_email, String pwd, String recevices, String content) {
    Properties props = new Properties();
    props.setProperty("mail.transport.protocol", "smtp");    //使用smpt的邮件传输协议
    props.setProperty("mail.smtp.host", "smtp.qq.com");    //主机地址
    props.setProperty("mail.smtp.auth", "true");    //授权通过

    Session session = Session.getInstance(props);    //通过我们的这些配置，得到一个会话程序

    try {

      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(from_email));    //设置发件人
      message.setRecipient(MimeMessage.RecipientType.TO,
          new InternetAddress(recevices, "用户", "utf-8"));    //设置收件人
      message.setSubject("验证码", "utf-8");    //设置主题
      message.setSentDate(new Date());


      Multipart mul = new MimeMultipart();  //新建一个MimeMultipart对象来存放多个BodyPart对象
      BodyPart mdp = new MimeBodyPart();  //新建一个存放信件内容的BodyPart对象
      mdp.setContent(content, "text/html;charset=utf-8");
      mul.addBodyPart(mdp);  //将含有信件内容的BodyPart加入到MimeMultipart对象中
      message.setContent(mul); //把mul作为消息内容

      message.saveChanges();

      //创建一个传输对象
      Transport transport = session.getTransport("smtp");

      //建立与服务器的链接  465端口是 SSL传输
      transport.connect("smtp.qq.com", 587, from_email, pwd);

      //发送邮件
      transport.sendMessage(message, message.getAllRecipients());

      //关闭邮件传输
      transport.close();

    } catch (AddressException e) {
      e.printStackTrace();
      return false;
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return false;
    } catch (MessagingException e) {
      e.printStackTrace();
      return false;
    }

    return true;
  }
}


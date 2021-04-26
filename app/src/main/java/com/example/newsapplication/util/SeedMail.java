package com.example.newsapplication.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SeedMail {
    //发送账户
    private static final String SENDER_NAME = "linshiyouxiangll1@126.com";
    //发送账户的密码
    private static final String SENDER_PASS = "RNRBKKFNXJEVLSAW";
    //接受账户
    private static final String RESEVER_NAME = "712965452@qq.com";
    //邮箱服务器
    private static final String VALUE_MAIL_HOST = "smtp.126.com";
    //邮箱服务器主机
    private static final String KEY_MAIL_HOST = "mail.smtp.host";
    //邮箱是否需要鉴权
    private static final String KEY_MAIL_AUTH = "mail.smtp.auth";
    //需要鉴权
    private static final String VALUE_MAIL_AUTH = "true";

    private MimeMessage mimeMessage;
    public SeedMail() {
    }
    public void setContent(String title,String content){
        Properties properties = System.getProperties();
        properties.put(KEY_MAIL_HOST, VALUE_MAIL_HOST);
        properties.put(KEY_MAIL_AUTH, VALUE_MAIL_AUTH);
        Session session = Session.getInstance(properties, getAuthenticator());
        //创建消息
        this.mimeMessage = new MimeMessage(session);
        try {
            //设置发送者
            mimeMessage.setFrom(new InternetAddress(SENDER_NAME));
            //设置接收者
            InternetAddress[] addresses = new InternetAddress[]{new InternetAddress(RESEVER_NAME)};
            mimeMessage.setRecipients(Message.RecipientType.TO, addresses);
            //设置邮件的主题
            mimeMessage.setSubject(title);
            //设置邮件的内容
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(content, "text/plain");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            mimeMessage.setContent(multipart);
            //设置发送时间
            mimeMessage.setSentDate(new Date());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private Authenticator getAuthenticator() {
        return new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_NAME, SENDER_PASS);
            }
        };
    }
    public void Seed() throws MessagingException {
        if(mimeMessage!= null){
            Transport.send(mimeMessage);
        }

    }

}

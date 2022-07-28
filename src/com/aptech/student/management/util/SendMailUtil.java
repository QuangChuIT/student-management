package com.aptech.student.management.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.StringWriter;
import java.util.Date;
import java.util.Properties;

public class SendMailUtil {

    private static SendMailUtil instance;
    private Properties properties;
    private Session session;
    private String password;
    private String username;

    private SendMailUtil() {
        try {
            this.properties = new Properties();
            properties.put("mail.smtp.host", SystemConfig.getInstance().getProperty("mail.smtp.host"));
            properties.put("mail.smtp.port", SystemConfig.getInstance().getProperty("mail.smtp.port"));
            properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
            properties.put("mail.smtp.starttls.enable", SystemConfig.getInstance().getProperty("mail.smtp.starttls.enable"));
            properties.put("mail.smtp.auth", SystemConfig.getInstance().getProperty("mail.smtp.auth"));
            username = SystemConfig.getInstance().getProperty("mail.user");
            password = SystemConfig.getInstance().getProperty("mail.password");
            // creates a new session with an authenticator
            Authenticator auth = new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            };
            this.session = Session.getInstance(properties, auth);
        } catch (Exception e) {
            LOGGER.error("Can't initialize mail config", e);
        }
    }

    public static SendMailUtil getInstance() {
        if (instance == null) {
            instance = new SendMailUtil();
        }
        return instance;
    }

    public void sendMail(String toAddress, String subject, String content) {
        try {
            // creates a new e-mail message
            Message msg = new MimeMessage(session);

            VelocityEngine velocityEngine = new VelocityEngine();
            velocityEngine.init();

            Template t = velocityEngine.getTemplate("velocity/forgot-password-template.vm");
            VelocityContext context = new VelocityContext();
            context.put("reset_password_url", "123455");
            StringWriter writer = new StringWriter();
            t.merge( context, writer );

            msg.setFrom(new InternetAddress(this.username));
            InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            // set plain text message
            msg.setContent(writer.toString(), "text/html");

            // sends the e-mail
            Transport.send(msg);
            LOGGER.info("Send mail success to " + toAddress);
        } catch (Exception e) {
            LOGGER.error("Send mail error", e);
        }
    }

    public void sendMailWithAttachment(String toAddress, String subject, String content, String[] attachFiles) {
        try {
            // creates a new e-mail message
            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(this.username));
            InternetAddress[] toAddresses = {new InternetAddress(toAddress)};
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(subject);
            msg.setSentDate(new Date());

            // creates message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(content, "text/html");

            // creates multi-part
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // adds attachments
            if (attachFiles != null && attachFiles.length > 0) {
                for (String filePath : attachFiles) {
                    MimeBodyPart attachPart = new MimeBodyPart();
                    attachPart.attachFile(filePath);

                    multipart.addBodyPart(attachPart);
                }
            }

            // sets the multi-part as e-mail's content
            msg.setContent(multipart);

            // sends the e-mail
            Transport.send(msg);
            LOGGER.info("Send mail success to " + toAddress);
        } catch (Exception e) {
            LOGGER.error("Send mail with attachment error", e);
        }

    }

    public static void main(String[] args) {
        String[] attachments = new String[] {"D:/shippingapp-337302-1d075939339f.json"};
        //SendMailUtil.getInstance().sendMailWithAttachment("chuvanquang96@gmail.com", "Xin chao quang", "Di nhau khong", attachments);
        SendMailUtil.getInstance().sendMail("chuvanquang96@gmail.com", "Xin chao quang", "Di nhau khong");
    }
    private final static Logger LOGGER = LogManager.getLogger(SendMailUtil.class);
}

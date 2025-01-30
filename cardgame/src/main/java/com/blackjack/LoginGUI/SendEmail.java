package com.blackjack.javaedition.LoginGUI;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {

   public static void sendMail(String to, String subject, String content) {

      // the email adress the email is getting sent from
      // never changes
      String from = "icsemailsender@gmail.com";

      // Assuming you are sending email from through gmails smtp
      String host = "smtp.gmail.com";

      // Get system properties
      Properties properties = System.getProperties();

      // Setup mail server
      properties.put("mail.smtp.host", host);
      properties.put("mail.smtp.port", "465");
      properties.put("mail.smtp.ssl.enable", "true");
      properties.put("mail.smtp.auth", "true");

      // Get the Session object.// and pass username and password
      Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

         protected PasswordAuthentication getPasswordAuthentication() {

            return new PasswordAuthentication("icsemailsender@gmail.com", "snxddkxlrfsgjfdw");

         }

      });

      // Used to debug SMTP issues
      session.setDebug(true);

      try {
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(from));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

         // Set Subject: header field
         message.setSubject(subject);

         // Now set the actual message
         message.setText(content);

         // Send message
         Transport.send(message);
      } catch (MessagingException mex) {
         mex.printStackTrace();
      }

   }
}
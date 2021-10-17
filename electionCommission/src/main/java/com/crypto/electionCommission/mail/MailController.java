package com.crypto.electionCommission.mail;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MailController {
	
   @RequestMapping(value = "/sendemail")
   public String sendEmail() {
	  try {
		  sendmail();
	      return "Email sent successfully";
	  }catch(Exception e) {
		  System.out.println(e);
		  return "Error in sending email";
	  }
   }   

   private void sendmail() throws AddressException, MessagingException, IOException {
	   Properties props = new Properties();
	   props.put("mail.smtp.auth", "true");
	   props.put("mail.smtp.starttls.enable", "true");
	   props.put("mail.smtp.host", "smtp.gmail.com");
	   props.put("mail.smtp.port", "587");
	   
	   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	      protected PasswordAuthentication getPasswordAuthentication() {
	         return new PasswordAuthentication("kirishikesan.es@gmail.com", "P@s$w0rd");
	      }
	   });
	   Message msg = new MimeMessage(session);
	   msg.setFrom(new InternetAddress("electionCommission@crypto.com", false));

	   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("krishi.rc97@gmail.com"));
	   msg.setSubject("RandomID");
	   msg.setContent("RandomID", "text/html");
	   msg.setSentDate(new Date());

	   MimeBodyPart messageBodyPart = new MimeBodyPart();
	   messageBodyPart.setContent("Tutorials point email", "text/html");

//	   Multipart multipart = new MimeMultipart();
//	   multipart.addBodyPart(messageBodyPart);
//	   MimeBodyPart attachPart = new MimeBodyPart();

//	   attachPart.attachFile("/var/tmp/image19.png");
//	   multipart.addBodyPart(attachPart);
//	   msg.setContent(multipart);
	   
	   Transport.send(msg);   
	}
}
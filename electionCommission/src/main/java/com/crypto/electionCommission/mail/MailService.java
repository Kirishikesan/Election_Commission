//package com.crypto.electionCommission.mail;
//
//import javax.mail.internet.MimeMessage;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//@Component
//public class MailService implements EmailService {
//
//    @Autowired
//    private JavaMailSender emailSender;
//
//    public void sendSimpleMessage(
//      String to, String subject, String text) {
//        
//        SimpleMailMessage message = new SimpleMailMessage(); 
//        message.setFrom("noreply@baeldung.com");
//        message.setTo(to); 
//        message.setSubject(subject); 
//        message.setText(text);
//        emailSender.send(message);
//        
//    }
package com.sang.prosangserver.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.sang.prosangserver.components.MailComponent;

@Service
public class SendEmailService {
	
    private final JavaMailSender emailSender;
    
    private final MailComponent mailComponent;
    
    public SendEmailService(JavaMailSender emailSender, MailComponent mailComponent) {
		this.emailSender = emailSender;
		this.mailComponent = mailComponent;
	}
    
    public void sendSimpleMessage(
    	String to, String subject, String text) {
    		SimpleMailMessage message = new SimpleMailMessage(); 
    	    message.setFrom("sang9c@gmail.com");
    	    message.setTo(to); 
    	    message.setSubject(subject); 
    	    message.setText(text);
    	    emailSender.send(message);
    }
    
    @Async
    public void sendAccountRegisterMail(String email, String name) throws MessagingException {
    	String subject = mailComponent.getAccountRegisterSubject();
    	String htmlMsg = mailComponent.getAccountRegisterBody(name); 
    	sendMessageWithHtmlTemplate(email, subject, htmlMsg);
    }
    
    @Async
    public void sendVerifyAccountMail(String email, String name) throws MessagingException {
    	String subject = mailComponent.getVerifyAccountSubject();
    	String htmlMsg = mailComponent.getVerifyAccountBody(name); 
    	sendMessageWithHtmlTemplate(email, subject, htmlMsg);
    }
    
    @Async
    public void sendResetAccountMail(String email, String name) throws MessagingException {
    	String subject = mailComponent.getVerifyAccountSubject();
    	String htmlMsg = mailComponent.getVerifyAccountBody(name); 
    	sendMessageWithHtmlTemplate(email, subject, htmlMsg);
    }
    
    @Async
    public void sendResetAccountPasswordMail(String email, String name) throws MessagingException {
    	String subject = mailComponent.getResetAccountPasswordSubject();
    	String htmlMsg = mailComponent.getResetAccountPasswordBody(name); 
    	sendMessageWithHtmlTemplate(email, subject, htmlMsg);
    }
    
    public void sendMessageWithHtmlTemplate(String to, String subject, String htmlMsg) throws MessagingException {
    	
    	MimeMessage message = emailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
    	message.setContent(htmlMsg, "text/html");
    	helper.setTo(to);
        helper.setSubject(subject);
        this.emailSender.send(message);
    }
}

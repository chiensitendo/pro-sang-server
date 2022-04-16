package com.sang.prosangserver.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sang.prosangserver.constants.MailConstants;
import com.sang.prosangserver.services.MessageService;

@Component
public class MailComponent {

	private final MessageService messageService;
	
	@Value("${client.name}")
	private String clientName;
	
	public MailComponent(MessageService messageService) {
		this.messageService = messageService;
	}
	
	public String getAccountRegisterSubject() {
		return String.format("[%s] %s", clientName, messageService.getMessage("mail.account.register.subject"));
	}
	
	public String getAccountRegisterBody(String name) {
		return MailConstants.ACCOUNT_REGISTER_EMAIL_TEMPLATE
				.replace("$name", name)
				.replace("$url", "google.com");
	}
	
	public String getVerifyAccountSubject() {
		return String.format("[%s] %s", clientName, messageService.getMessage("mail.account.verify.subject"));
	}
	
	public String getVerifyAccountBody(String name) {
		return MailConstants.VERIFY_ACCOUNT_EMAIL_TEMPLATE
				.replace("$name", name)
				.replace("$url", "google.com");
	}
	
	public String getResetAccountPasswordSubject() {
		return String.format("[%s] %s", clientName, messageService.getMessage("mail.account.password.reset.subject"));
	}
	
	public String getResetAccountPasswordBody(String name) {
		return MailConstants.RESET_ACCOUNT_EMAIL_TEMPLATE
				.replace("$name", name)
				.replace("$url", "google.com");
	}
	
}

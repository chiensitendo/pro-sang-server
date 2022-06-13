package com.sang.prosangserver.services;

import com.sang.prosangserver.enums.EnumMessageInterface;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

	private final MessageSource messageSource;
	
	public MessageService(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public String getMessage(EnumMessageInterface message) {
		return messageSource.getMessage(message.getMessage(), null, LocaleContextHolder.getLocale());
	}
	
	public String getMessage(String message) {
		return messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
	}
}

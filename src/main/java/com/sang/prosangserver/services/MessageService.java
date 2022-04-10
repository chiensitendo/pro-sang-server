package com.sang.prosangserver.services;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.sang.prosangserver.enums.ErrorMessages;

@Service
public class MessageService {

	private final MessageSource messageSource;
	
	public MessageService(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	public String getMessage(ErrorMessages message) {
		return messageSource.getMessage(message.getMessage(), null, LocaleContextHolder.getLocale());
	}
}

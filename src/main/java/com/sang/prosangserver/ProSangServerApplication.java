package com.sang.prosangserver;

import java.util.Locale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ProSangServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProSangServerApplication.class, args);
	}
	
	@Bean  
	public LocaleResolver localeResolver() {  
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.US);  
		return localeResolver;  
	}
	
	@Bean  
	public ResourceBundleMessageSource messageSource() {  
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("messages", "error-messages");
		return messageSource;  
	}  

}

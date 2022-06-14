package com.sang.prosangserver.services;

import java.util.ArrayList;

import com.sang.prosangserver.models.AuthUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.sang.prosangserver.entities.account.Account;
import com.sang.prosangserver.enums.ErrorMessages;
import com.sang.prosangserver.exceptions.UserNotFoundException;
import com.sang.prosangserver.repositories.AccountRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	private final AccountRepository accountRepository;
	
	private final MessageService messageService;
	
	public JwtUserDetailsService(AccountRepository accountRepository, MessageService messageService) {
		this.accountRepository = accountRepository;
		this.messageService = messageService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		
		Account account = getValidAccountByUserName(username);
		return new AuthUser(account.getUsername(), account.getAuth().getPassword(), new ArrayList<>(), account.getId());
	}
	
	public Account getValidAccountByUserName(String username) {
		return accountRepository.getOneByUsernameAndIsDeletedIsFalse(username)
				.orElseThrow(() -> new UserNotFoundException(messageService.getMessage(ErrorMessages.USER_NOTFOUND)));
	}

}

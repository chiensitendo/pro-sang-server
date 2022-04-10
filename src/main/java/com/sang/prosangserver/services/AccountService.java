package com.sang.prosangserver.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.sang.prosangserver.dto.response.UserDetailResponse;
import com.sang.prosangserver.entities.Account;
import com.sang.prosangserver.enums.ErrorMessages;
import com.sang.prosangserver.exceptions.UserNotFoundException;
import com.sang.prosangserver.mappers.AccountUserDetailMapper;
import com.sang.prosangserver.repositories.AccountRepository;

@Service
public class AccountService {
	
	private final AccountRepository accountRepository;
	
	private final AccountUserDetailMapper accountUserDetailMapper;
	
	private final MessageService messageService; 
	
	public AccountService(
			AccountRepository accountRepository, 
			AccountUserDetailMapper accountUserDetailMapper,
			MessageService messageService) {
		this.accountRepository = accountRepository;
		this.accountUserDetailMapper = accountUserDetailMapper;
		this.messageService = messageService;
	}
	
	public List<UserDetailResponse> getAccountList() {
		return accountRepository.findAll().stream()
				.map(accountUserDetailMapper::accountToUserDetail).collect(Collectors.toList());
	}
	
	public UserDetailResponse getAccountDetail(Long id) {
		Account acc = accountRepository.getOneByIdAndIsDeletedIsFalse(id)
				.orElseThrow(() -> new UserNotFoundException(messageService.getMessage(ErrorMessages.USER_NOTFOUND)));
		return accountUserDetailMapper.accountToUserDetail(acc);
	}
}

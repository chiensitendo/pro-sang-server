package com.sang.prosangserver.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sang.prosangserver.dto.response.UserDetailResponse;
import com.sang.prosangserver.entities.Account;
import com.sang.prosangserver.exceptions.UserNotFoundException;
import com.sang.prosangserver.mappers.AccountUserDetailMapper;
import com.sang.prosangserver.repositories.AccountRepository;

@Service
public class AccountService {
	
	private final AccountRepository accountRepository;
	
	private final AccountUserDetailMapper accountUserDetailMapper;
	
	
	public AccountService(AccountRepository accountRepository, AccountUserDetailMapper accountUserDetailMapper) {
		this.accountRepository = accountRepository;
		this.accountUserDetailMapper = accountUserDetailMapper;
	}
	
	public List<UserDetailResponse> getAccountList() {
		return accountRepository.findAll().stream()
				.map(accountUserDetailMapper::accountToUserDetail).collect(Collectors.toList());
	}
	
	public UserDetailResponse getAccountDetail(Long id) {
		Account acc = accountRepository.getOneByIdAndIsDeletedIsFalse(id).orElseThrow(() -> new UserNotFoundException("this user is not found"));
		return accountUserDetailMapper.accountToUserDetail(acc);
	}
}

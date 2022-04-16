package com.sang.prosangserver.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sang.prosangserver.constants.AuthConstants;
import com.sang.prosangserver.dto.request.CreateAccountRequest;
import com.sang.prosangserver.dto.request.UpdateAccountDetailRequest;
import com.sang.prosangserver.dto.response.CreateAccountResponse;
import com.sang.prosangserver.dto.response.UserDetailResponse;
import com.sang.prosangserver.entities.account.Account;
import com.sang.prosangserver.entities.account.AccountAuth;
import com.sang.prosangserver.entities.account.AccountDetail;
import com.sang.prosangserver.enums.ErrorMessages;
import com.sang.prosangserver.exceptions.UserExistsException;
import com.sang.prosangserver.exceptions.UserNotFoundException;
import com.sang.prosangserver.mappers.AccountUserDetailMapper;
import com.sang.prosangserver.mappers.CreateAccountRequestToAccountMapper;
import com.sang.prosangserver.mappers.UpdateAccountDetailToAccountDetailMapper;
import com.sang.prosangserver.repositories.AccountRepository;
import com.sang.prosangserver.utils.ObjectUtils;

@Service
public class AccountService {
	
	private final AccountRepository accountRepository;
	
	private final AccountUserDetailMapper accountUserDetailMapper;
	
	private final UpdateAccountDetailToAccountDetailMapper updateDetailMapper;
	
	private final MessageService messageService;
	
	private final CreateAccountRequestToAccountMapper requestToEntityMapper;
	
	private final PasswordEncoder passwordEncoder;
	
	private final SendEmailService sendEmailService;
	
	@Value("${debug}")
	private Boolean isDebug;
	
	public AccountService(
			AccountRepository accountRepository, 
			AccountUserDetailMapper accountUserDetailMapper,
			MessageService messageService,
			CreateAccountRequestToAccountMapper requestToEntityMapper,
			UpdateAccountDetailToAccountDetailMapper updateDetailMapper,
			PasswordEncoder passwordEncoder,
			SendEmailService sendEmailService) {
		this.accountRepository = accountRepository;
		this.accountUserDetailMapper = accountUserDetailMapper;
		this.messageService = messageService;
		this.requestToEntityMapper = requestToEntityMapper;
		this.updateDetailMapper = updateDetailMapper;
		this.passwordEncoder = passwordEncoder;
		this.sendEmailService = sendEmailService;
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
	
	public CreateAccountResponse createAccount(CreateAccountRequest request) {
		if(accountRepository
			.getOneByEmailOrUsernameAndIsDeletedIsFalse(request.getEmail(), request.getUsername())
			.isPresent()) {
			throw new UserExistsException(messageService.getMessage(ErrorMessages.USER_EXISTS));
		}
		Account acc = requestToEntityMapper.createAccountRequestToAccount(request);
		acc.setAuth(new AccountAuth());
		acc.getAuth().setPassword(passwordEncoder.encode(request.getPassword()));
		Account savedAcc = accountRepository.saveAndFlush(acc);
		savedAcc.getAuth().setPasswordExpiredTime(LocalDateTime.now().plusMonths(AuthConstants.PASSWORD_EXPIRED_MONTHS));
		savedAcc.getAuth().setAccount(savedAcc);
		savedAcc.getDetail().setAccount(savedAcc);
		accountRepository.saveAndFlush(savedAcc);
		try {
			if (!isDebug) {
				sendEmailService.sendAccountRegisterMail(savedAcc.getEmail(), savedAcc.getDetail().getLastName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new CreateAccountResponse(savedAcc.getUsername());
	}
	
	public UserDetailResponse updateAccountDetail(Long id, UpdateAccountDetailRequest request) {
		Account acc = getValidAccountById(id);
		AccountDetail source = updateDetailMapper.requestToAccountDetail(request);
		ObjectUtils.copyProperties(source, acc.getDetail());
		accountRepository.saveAndFlush(acc);
		return accountUserDetailMapper.accountToUserDetail(acc);
	}
	
	public Account getValidAccountById(Long id) {
		return accountRepository.getOneByIdAndIsDeletedIsFalse(id)
				.orElseThrow(() -> new UserNotFoundException(messageService.getMessage(ErrorMessages.USER_NOTFOUND)));
	}
}

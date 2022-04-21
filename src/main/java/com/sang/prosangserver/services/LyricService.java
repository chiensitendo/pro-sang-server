package com.sang.prosangserver.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.sang.prosangserver.dto.request.lyric.LyricRequest;
import com.sang.prosangserver.entities.account.Account;
import com.sang.prosangserver.entities.lyric.Lyric;
import com.sang.prosangserver.enums.ErrorMessages;
import com.sang.prosangserver.exceptions.UnauthorizedException;
import com.sang.prosangserver.exceptions.UserNotFoundException;
import com.sang.prosangserver.mappers.LyricMapper;
import com.sang.prosangserver.repositories.AccountRepository;
import com.sang.prosangserver.repositories.LyricRepository;

@Service
public class LyricService {

	private final LyricRepository lyricRepository;
	
	private final AccountRepository accountRepository;
	
	private final MessageService messageService;
	
	private final LyricMapper lyricMapper;
	
	public LyricService(LyricRepository lyricRepository, 
						AccountRepository accountRepository,
						MessageService messageService,
						LyricMapper lyricMapper) {
		this.lyricRepository = lyricRepository;
		this.accountRepository = accountRepository;
		this.messageService = messageService;
		this.lyricMapper = lyricMapper;
	}
	
	public void createLyric(LyricRequest lyricRequest) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.isAuthenticated()) {
			throw new UnauthorizedException();
		}
		Object principal = auth.getPrincipal();
		if (principal instanceof UserDetails) {
			UserDetails detail = (UserDetails) principal;
			Account account = getValidAccountByUserName(detail.getUsername());
			Lyric lyric = lyricMapper.lyricRequestToLyricEntity(lyricRequest);
			lyric.setAccount(account);
			lyricRepository.saveAndFlush(lyric);
		} else {
			throw new UnauthorizedException();
		}
	}
	
	private Account getValidAccountByUserName(String username) {
		return accountRepository.getOneByUsernameAndIsDeletedIsFalse(username)
				.orElseThrow(() -> new UserNotFoundException(messageService.getMessage(ErrorMessages.USER_NOTFOUND)));
	}
}

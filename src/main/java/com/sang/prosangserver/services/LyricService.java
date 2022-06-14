package com.sang.prosangserver.services;

import com.sang.prosangserver.dto.response.lyric.LyricCommentUserInfo;
import com.sang.prosangserver.dto.response.lyric.LyricDetailResponse;
import com.sang.prosangserver.dto.response.lyric.LyricListResponse;
import com.sang.prosangserver.enums.lyric.LyricMessages;
import com.sang.prosangserver.exceptions.LyricNotFoundException;
import com.sang.prosangserver.models.AuthUser;
import org.springframework.stereotype.Service;

import com.sang.prosangserver.dto.request.lyric.LyricRequest;
import com.sang.prosangserver.entities.account.Account;
import com.sang.prosangserver.entities.lyric.Lyric;
import com.sang.prosangserver.enums.ErrorMessages;
import com.sang.prosangserver.exceptions.UserNotFoundException;
import com.sang.prosangserver.mappers.LyricMapper;
import com.sang.prosangserver.repositories.AccountRepository;
import com.sang.prosangserver.repositories.LyricRepository;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LyricService {

	private final LyricRepository lyricRepository;
	
	private final AccountRepository accountRepository;
	
	private final MessageService messageService;
	
	private final LyricMapper lyricMapper;

	private final AuthService authService;

	public LyricService(LyricRepository lyricRepository, 
						AccountRepository accountRepository,
						MessageService messageService,
						LyricMapper lyricMapper,
						AuthService authService) {
		this.lyricRepository = lyricRepository;
		this.accountRepository = accountRepository;
		this.messageService = messageService;
		this.lyricMapper = lyricMapper;
		this.authService = authService;
	}
	
	public void createLyric(LyricRequest lyricRequest) {
		AuthUser authUser = authService.getAuthUser();
		Account account = getValidAccountByUserName(authUser.getUsername());
		Lyric lyric = lyricMapper.lyricRequestToLyricEntity(lyricRequest);
		lyric.setAccount(account);
		lyricRepository.saveAndFlush(lyric);
	}

	public LyricListResponse getAccountLyricList() {
		AuthUser authUser = authService.getAuthUser();
		return LyricListResponse.builder()
				.accountId(authUser.getId())
				.lyrics(lyricRepository.findAllByCurrentUserId(authUser.getId()))
				.build();
	}

	public LyricListResponse getAccountLyricListByAccountId(Long accountId) {
		AuthUser authUser = authService.getAuthUser();
		checkValidAccount(accountId);
		LyricListResponse.LyricListResponseBuilder builder = LyricListResponse.builder().accountId(accountId);
		if (authUser.getId().equals(accountId)) {
			builder.lyrics(lyricRepository.findAllByCurrentUserId(accountId));
		} else {
			builder.lyrics(lyricRepository.findAllByAccountId(accountId));
		}
		return builder.build();
	}

	public LyricDetailResponse getLyricDetail(Long id) {
		AuthUser authUser = authService.getAuthUser();
		Lyric lyric = lyricRepository.findLyricWithCurrentAccountId(id, authUser.getId())
						.orElseThrow(() -> new LyricNotFoundException(messageService.getMessage(LyricMessages.LYRIC_NOTFOUND)));
		Set<Long> accIds = lyric.getLyricComments().stream().map(l -> l.getAccountId()).collect(Collectors.toSet());
		Map<Long, LyricCommentUserInfo> userInfos = lyricRepository.getLyricCommentUserInfoList(accIds).stream().collect(
			Collectors.toMap(
				tuple -> tuple.getId(),
				tuple -> tuple
			)
		);
		return lyricMapper.toLyricDetail(lyric).updateUserInfo(userInfos);
	}
	
	private Account getValidAccountByUserName(String username) {
		return accountRepository.getOneByUsernameAndIsDeletedIsFalse(username)
				.orElseThrow(() -> new UserNotFoundException(messageService.getMessage(ErrorMessages.USER_NOTFOUND)));
	}

	private void checkValidAccount(Long accountId) {
		Boolean isValid = accountRepository.existsAccountByIdAndIsDeletedIsFalse(accountId);
		if (!isValid) {
			throw new UserNotFoundException(messageService.getMessage(ErrorMessages.USER_NOTFOUND));
		}
	}
}

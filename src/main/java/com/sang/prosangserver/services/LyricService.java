package com.sang.prosangserver.services;

import com.sang.prosangserver.constants.LyricConstants;
import com.sang.prosangserver.dto.request.lyric.LyricAddCommentRequest;
import com.sang.prosangserver.dto.request.lyric.LyricAddReplyRequest;
import com.sang.prosangserver.dto.request.lyric.LyricLikeCommentRequest;
import com.sang.prosangserver.dto.request.lyric.LyricLikeReplyRequest;
import com.sang.prosangserver.dto.request.lyric.LyricRateRequest;
import com.sang.prosangserver.dto.response.lyric.AccountLyricItemResponse;
import com.sang.prosangserver.dto.response.lyric.LyricAddCommentResponse;
import com.sang.prosangserver.dto.response.lyric.LyricAddReplyResponse;
import com.sang.prosangserver.dto.response.lyric.LyricCommentUserInfo;
import com.sang.prosangserver.dto.response.lyric.LyricDetailResponse;
import com.sang.prosangserver.dto.response.lyric.LyricEditDetailResponse;
import com.sang.prosangserver.dto.response.lyric.LyricInfoDetailResponse;
import com.sang.prosangserver.dto.response.lyric.LyricLikeCommentResponse;
import com.sang.prosangserver.dto.response.lyric.LyricLikeReplyResponse;
import com.sang.prosangserver.dto.response.lyric.LyricListAccountResponse;
import com.sang.prosangserver.dto.response.lyric.LyricListItem;
import com.sang.prosangserver.dto.response.lyric.LyricListResponse;
import com.sang.prosangserver.dto.response.lyric.LyricLoadMoreResponse;
import com.sang.prosangserver.dto.response.lyric.LyricRateResponse;
import com.sang.prosangserver.dto.response.lyric.LyricRepliedCommentResponse;
import com.sang.prosangserver.dto.response.lyric.info.LyricCommentBaseItem;
import com.sang.prosangserver.dto.response.lyric.info.LyricCommentItem;
import com.sang.prosangserver.entities.lyric.LyricAccountLike;
import com.sang.prosangserver.entities.lyric.LyricComment;
import com.sang.prosangserver.entities.lyric.LyricReplyComment;
import com.sang.prosangserver.enums.lyric.LyricMessages;
import com.sang.prosangserver.enums.lyric.LyricStatuses;
import com.sang.prosangserver.exceptions.LyricCommentNotFoundException;
import com.sang.prosangserver.exceptions.LyricException;
import com.sang.prosangserver.exceptions.LyricNotFoundException;
import com.sang.prosangserver.models.AuthUser;
import com.sang.prosangserver.models.LyricDetailModel;
import com.sang.prosangserver.models.LyricLMCommentModel;
import com.sang.prosangserver.models.LyricRepliedCommentModel;
import com.sang.prosangserver.repositories.LyricAccountLikeRepository;
import com.sang.prosangserver.repositories.LyricCommentRepository;
import com.sang.prosangserver.repositories.LyricReplyCommentRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sang.prosangserver.dto.request.lyric.LyricRequest;
import com.sang.prosangserver.entities.account.Account;
import com.sang.prosangserver.entities.lyric.Lyric;
import com.sang.prosangserver.enums.ErrorMessages;
import com.sang.prosangserver.exceptions.UserNotFoundException;
import com.sang.prosangserver.mappers.LyricMapper;
import com.sang.prosangserver.repositories.AccountRepository;
import com.sang.prosangserver.repositories.LyricRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LyricService {

	private final LyricRepository lyricRepository;

	private final LyricCommentRepository lyricCommentRepository;

	private final LyricAccountLikeRepository lyricAccountLikeRepository;

	private final LyricReplyCommentRepository lyricReplyCommentRepository;

	private final AccountRepository accountRepository;

	private final MessageService messageService;

	private final LyricMapper lyricMapper;

	private final AuthService authService;

	public LyricService(LyricRepository lyricRepository,
						AccountRepository accountRepository,
						MessageService messageService,
						LyricMapper lyricMapper,
						AuthService authService,
						LyricCommentRepository lyricCommentRepository,
						LyricAccountLikeRepository lyricAccountLikeRepository,
						LyricReplyCommentRepository lyricReplyCommentRepository) {
		this.lyricRepository = lyricRepository;
		this.accountRepository = accountRepository;
		this.messageService = messageService;
		this.lyricMapper = lyricMapper;
		this.authService = authService;
		this.lyricCommentRepository = lyricCommentRepository;
		this.lyricAccountLikeRepository = lyricAccountLikeRepository;
		this.lyricReplyCommentRepository = lyricReplyCommentRepository;
	}

	public void addLyric(LyricRequest request) {
		AuthUser authUser = authService.getAuthUser();
		authService.checkCorrectAccountId(authUser, request.getAccountId());
		request.setTitle(request.getTitle().trim());
		if (lyricRepository.existsByTitleAndIsDeletedIsFalse(request.getTitle()) != null) {
			throw new LyricException(messageService.getMessage(LyricMessages.LYRIC_EXISTED_TITLE));
		}
		Account account = getValidAccountByUserName(authUser.getUsername());
		Lyric lyric = lyricMapper.lyricRequestToLyricEntity(request);
		lyric.setAccount(account);
		lyricRepository.saveAndFlush(lyric);
	}

	public LyricListAccountResponse getAccountLyricList(Integer offset) {
		AuthUser authUser = authService.getAuthUser();
		Pageable pageable = PageRequest.of(offset, LyricConstants.MAX_ITEM_PER_PAGE, Sort.by("id").descending());
		Page<AccountLyricItemResponse> page = lyricRepository.findAllByCurrentUserId(pageable, authUser.getId());
		return LyricListAccountResponse.builder()
				.accountId(authUser.getId())
				.total(page.getTotalElements())
				.lyrics(page.getContent())
				.build();
	}

	public LyricListAccountResponse getAccountLyricListByAccountId(Long accountId) {
		AuthUser authUser = authService.getAuthUser();
		checkValidAccount(accountId);
		LyricListAccountResponse.LyricListAccountResponseBuilder builder = LyricListAccountResponse.builder().accountId(accountId);
		Pageable pageable = PageRequest.of(0, LyricConstants.MAX_ITEM_PER_PAGE, Sort.by("id").descending());
		Page<AccountLyricItemResponse> page = lyricRepository.findAllByCurrentUserId(pageable, accountId);
		if (authUser.getId().equals(accountId)) {
			builder.lyrics(page.getContent());
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

	public LyricInfoDetailResponse getLyricInfoDetailByRef(String ref) {
		AuthUser authUser = authService.getAuthUserButNotRequired();
		LyricInfoDetailResponse response = null;
		if (NumberUtils.isCreatable(ref)) {
			Long accId = authUser == null ? -1L: authUser.getId();
			Optional<LyricDetailModel> optionalLyricDetailModel =
				lyricRepository.getLyricDetailById(Long.parseLong(ref), LyricConstants.MAX_COMMENT_PER_PAGE, accId, "%" + accId + "%");
			if (optionalLyricDetailModel.isPresent()) {
				LyricDetailModel model = optionalLyricDetailModel.get();
				response = lyricMapper.lyricDetailModelToLyricInfoDetailResponse(model);
			}
		}
		if (response == null) {
			throw new LyricNotFoundException(messageService.getMessage(LyricMessages.LYRIC_NOTFOUND));
		}
		return response;
	}

	public LyricRepliedCommentResponse getLyricRepliedComments(Long lyricId, Long commendId, Integer offset) {

		AuthUser authUser = authService.getAuthUserButNotRequired();
		if (!lyricRepository.existsByIdAndIsDeletedIsFalse(lyricId)) {
			throw new LyricNotFoundException(messageService.getMessage(LyricMessages.LYRIC_NOTFOUND));
		}
		if (!lyricCommentRepository.existsByIdAndIsDeletedIsFalse(commendId)) {
			throw new LyricCommentNotFoundException(messageService.getMessage(LyricMessages.LYRIC_COMMENT_NOTFOUND));
		}
		LyricRepliedCommentResponse response = new LyricRepliedCommentResponse();
		List<LyricRepliedCommentModel> results = lyricRepository.getRepliedComments(commendId, LyricConstants.MAX_REPLY_PER_COMMENT, offset, authUser == null ? -1L : authUser.getId());
		List<LyricCommentBaseItem> repliedItems = results.stream().map(reply -> lyricMapper.toLyricCommentBaseItem(reply)).collect(Collectors.toList());
		response.setCommentId(commendId);
		Collections.reverse(repliedItems);
		response.setReplies(repliedItems);
		return response;
	}

	public LyricLikeCommentResponse likeComment(LyricLikeCommentRequest request) {
		AuthUser authUser = authService.getAuthUser();
		Optional<LyricComment> optionalLyricComment = lyricCommentRepository.getLyricCommentByIdAndAccountIdAndIsDeletedIsFalse(request.getCommentId(), request.getAccountId());
		if (!optionalLyricComment.isPresent()) {
			throw new LyricCommentNotFoundException(messageService.getMessage(LyricMessages.LYRIC_COMMENT_NOTFOUND));
		}
		LyricComment comment = optionalLyricComment.get();
		Boolean isPresent = lyricAccountLikeRepository.existsByCommentIdAndAccountIdAndIsDeletedIsFalse(request.getCommentId(), authUser.getId());
		if (isPresent && !request.getIsLiked()) {
			lyricAccountLikeRepository.deleteByCommentIdAndAccountIdAndIsDeletedIsFalse(request.getCommentId(), authUser.getId());
			comment.setLikes((comment.getLikes() == null || comment.getLikes() == 0) ? 0L: comment.getLikes() - 1L);
			lyricCommentRepository.saveAndFlush(comment);
		} else if (!isPresent && request.getIsLiked()) {
			LyricAccountLike like = new LyricAccountLike();
			like.setCommentId(request.getCommentId());
			like.setAccountId(authUser.getId());
			lyricAccountLikeRepository.saveAndFlush(like);
			comment.setLikes((comment.getLikes() == null || comment.getLikes() == 0 )? 1L: comment.getLikes() + 1L);
			lyricCommentRepository.saveAndFlush(comment);
		}
		return new LyricLikeCommentResponse(request.getCommentId(), request.getAccountId(), request.getIsLiked(), comment.getLikes());
	}

	public LyricLikeReplyResponse likeReply(LyricLikeReplyRequest request) {
		AuthUser authUser = authService.getAuthUser();
		Optional<LyricReplyComment> replyCommentOptional =
			lyricReplyCommentRepository.getReplyByIdAndAccountIdAndIsDeletedIsFalse(request.getReplyId(), request.getAccountId(), request.getCommentId());
		if (!replyCommentOptional.isPresent()) {
			throw new LyricCommentNotFoundException(messageService.getMessage(LyricMessages.LYRIC_COMMENT_NOTFOUND));
		}
		LyricReplyComment replyComment = replyCommentOptional.get();
		Boolean isPresent = lyricAccountLikeRepository.existsByCommentIdAndAccountIdAndReplyIdAndIsDeletedIsFalse(request.getCommentId(), request.getAccountId(), request.getReplyId());
		if (isPresent && !request.getIsLiked()) {
			lyricAccountLikeRepository.deleteByCommentIdAndAccountIdAndReplyIdAndIsDeletedIsFalse(request.getCommentId(), request.getAccountId(), request.getReplyId());
			replyComment.setLikes((replyComment.getLikes() == null || replyComment.getLikes() == 0) ? 0L: replyComment.getLikes() - 1L);
			lyricReplyCommentRepository.saveAndFlush(replyComment);
		} else if (!isPresent && request.getIsLiked()) {
			LyricAccountLike like = new LyricAccountLike();
			like.setCommentId(request.getCommentId());
			like.setAccountId(authUser.getId());
			like.setReplyId(request.getReplyId());
			lyricAccountLikeRepository.saveAndFlush(like);
			replyComment.setLikes((replyComment.getLikes() == null || replyComment.getLikes() == 0) ? 1L: replyComment.getLikes() + 1L);
			lyricReplyCommentRepository.saveAndFlush(replyComment);
		}
		return new LyricLikeReplyResponse(request.getCommentId(), request.getAccountId(), request.getIsLiked(), replyComment.getLikes(), request.getReplyId());
	}

	public LyricAddCommentResponse addNewComment(LyricAddCommentRequest request) {
		AuthUser authUser = authService.getAuthUser();
		authService.checkCorrectAccountId(authUser, request.getAccountId());
		Optional<Lyric> lyricOpt = lyricRepository.getByIdAndAccountIdAndIsDeletedIsFalse(request.getLyricId(), request.getAccountId());
		if (!lyricOpt.isPresent()) {
			throw new LyricNotFoundException(messageService.getMessage(LyricMessages.LYRIC_NOTFOUND));
		}
		LyricComment comment = new LyricComment();
		comment.setLyric(lyricOpt.get());
		comment.setAccountId(request.getAccountId());
		comment.setContent(request.getContent());
		lyricCommentRepository.saveAndFlush(comment);

		return new LyricAddCommentResponse(
			comment.getId(),
			comment.getContent(),
			comment.getLikes(),
			false,
			request.getUserInfo(),
			comment.getCreatedDate(),
			comment.getUpdatedDate(),
			comment.getStars(),
			null);
	}

	public LyricRateResponse rateLyric(Long lyricId, LyricRateRequest request) {
		AuthUser authUser = authService.getAuthUser();
		Optional<Lyric> lyricOpt = lyricRepository.getByIdAndIsDeletedIsFalse(lyricId);
		if (!lyricOpt.isPresent()) {
			throw new LyricNotFoundException(messageService.getMessage(LyricMessages.LYRIC_NOTFOUND));
		}
		Lyric lyric = lyricOpt.get();
		LyricComment comment = new LyricComment();
		comment.setLyric(lyricOpt.get());
		comment.setAccountId(authUser.getId());
		comment.setContent("");
		comment.setStars(request.getStars());
		lyricCommentRepository.saveAndFlush(comment);
		Long total = lyric.getRate() == null ? 0L: Math.round((lyric.getStars() != null ? lyric.getStars(): 0)/lyric.getRate());
		lyric.setStars(lyric.getStars() == null ? request.getStars() : lyric.getStars() + request.getStars());
		lyric.setRate(lyric.getStars()/(total + 1));
		lyric.setRateAccountList(lyric.getRateAccountList() == null ? String.valueOf(authUser.getId()): lyric.getRateAccountList() + ","+ authUser.getId());
		lyricRepository.saveAndFlush(lyric);
		LyricAddCommentResponse commentResponse = new LyricAddCommentResponse(
			comment.getId(),
			comment.getContent(),
			comment.getLikes(),
			false,
			request.getUserInfo(),
			comment.getCreatedDate(),
			comment.getUpdatedDate(),
			comment.getStars(),
			null);

		return new LyricRateResponse(lyric.getRate(), request.getStars(), commentResponse);
	}

	public LyricAddReplyResponse addNewReplyComment(Long lyricId, Long commentId, LyricAddReplyRequest request) {
		AuthUser authUser = authService.getAuthUser();
		authService.checkCorrectAccountId(authUser, request.getAccountId());
		Optional<Lyric> lyricOpt = lyricRepository.getByIdAndIsDeletedIsFalse(lyricId);
		if (!lyricOpt.isPresent()) {
			throw new LyricNotFoundException(messageService.getMessage(LyricMessages.LYRIC_NOTFOUND));
		}
		Optional<LyricComment> optionalLyricComment = lyricCommentRepository.getByIdAndIsDeletedIsFalse(commentId);
		if (!optionalLyricComment.isPresent()) {
			throw new LyricCommentNotFoundException(messageService.getMessage(LyricMessages.LYRIC_COMMENT_NOTFOUND));
		}
		LyricReplyComment replyComment = new LyricReplyComment();
		replyComment.setContent(request.getContent());
		replyComment.setComment(optionalLyricComment.get());
		replyComment.setAccountId(request.getAccountId());
		lyricReplyCommentRepository.saveAndFlush(replyComment);
		return new LyricAddReplyResponse(
			replyComment.getId(), commentId, replyComment.getContent(), request.getUserInfo(), replyComment.getCreatedDate(), replyComment.getUpdatedDate()
			);
	}

	public LyricLoadMoreResponse loadMoreComments(Long lyricId, Long offset) {

		AuthUser authUser = authService.getAuthUserButNotRequired();
		if (!lyricRepository.existsByIdAndIsDeletedIsFalse(lyricId)) {
			throw new LyricNotFoundException(messageService.getMessage(LyricMessages.LYRIC_NOTFOUND));
		}

		List<LyricLMCommentModel> results = lyricCommentRepository.loadMoreCommentByLyricId(lyricId, authUser == null ? -1L: authUser.getId(), LyricConstants.MAX_COMMENT_PER_PAGE, offset);
		List<LyricCommentItem> items = results.stream().map(c -> lyricMapper.toLyricCommentItemResponse(c)).collect(Collectors.toList());
		Collections.reverse(items);
		return new LyricLoadMoreResponse(items);
	}

	public LyricEditDetailResponse getLyricDetailForEdit(Long lyricId) {
		AuthUser authUser = authService.getAuthUser();
		Optional<Lyric> lyricOpt = lyricRepository.getByIdAndAccountIdAndIsDeletedIsFalse(lyricId, authUser.getId());
		if (!lyricOpt.isPresent()) {
			throw new LyricNotFoundException(messageService.getMessage(LyricMessages.LYRIC_NOTFOUND));
		}
		LyricEditDetailResponse response = lyricMapper.LyricEditDetailResponse(lyricOpt.get());
		response.setAccountId(authUser.getId());
		return response;
	}

	public void editLyric(Long lyricId, LyricRequest request) {
		AuthUser authUser = authService.getAuthUser();
		Optional<Lyric> lyricOpt = lyricRepository.getByIdAndAccountIdAndIsDeletedIsFalse(lyricId, authUser.getId());
		if (!lyricOpt.isPresent()) {
			throw new LyricNotFoundException(messageService.getMessage(LyricMessages.LYRIC_NOTFOUND));
		}
		request.setTitle(request.getTitle().trim());
		if (lyricRepository.existsByTitleAndIdIsNotEqualsAndIsDeletedIsFalse(request.getTitle(), lyricId) != null) {
			throw new LyricException(messageService.getMessage(LyricMessages.LYRIC_EXISTED_TITLE));
		}
		Lyric lyric = lyricOpt.get();
		if (StringUtils.isNotEmpty(request.getTitle())) {
			lyric.setTitle(request.getTitle());
		}
		if (StringUtils.isNotEmpty(request.getContent())) {
			lyric.setContent(request.getContent());
		}
		if (StringUtils.isNotEmpty(request.getDescription())){
			lyric.setDescription(request.getDescription());
		}
		if (StringUtils.isNotEmpty(request.getComposers())) {
			lyric.setComposers(request.getComposers());
		}
		if (StringUtils.isNotEmpty(request.getSingers())) {
			lyric.setSingers(request.getSingers());
		}
		if (StringUtils.isNotEmpty(request.getOwners())) {
			lyric.setOwners(request.getOwners());
		}
		if (request.getStatus() != null) {
			lyric.setStatus(LyricStatuses.getStatus(request.getStatus()));
		}
		lyricRepository.saveAndFlush(lyric);
	}

	public void deleteLyric(Long lyricId) {
		AuthUser authUser = authService.getAuthUser();
		Optional<Lyric> lyricOpt = lyricRepository.getByIdAndAccountIdAndIsDeletedIsFalse(lyricId, authUser.getId());
		if (!lyricOpt.isPresent()) {
			throw new LyricNotFoundException(messageService.getMessage(LyricMessages.LYRIC_NOTFOUND));
		}
		lyricRepository.delete(lyricOpt.get());
	}

	public LyricListResponse getLyricList(Integer offset, String searchText) {
		Pageable pageable = PageRequest.of(offset, LyricConstants.MAX_ITEM_PER_PAGE, Sort.by("id").descending());
		Page<LyricListItem> page = lyricRepository.getPublicLyricList(pageable, StringUtils.isNotEmpty(searchText)? "%" + searchText + "%": null);
		return LyricListResponse.builder()
			.total(page.getTotalElements())
			.items(page.getContent()).build();
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

package com.sang.prosangserver.mappers;

import com.sang.prosangserver.constants.LyricConstants;
import com.sang.prosangserver.dto.response.lyric.LyricCommentItem;
import com.sang.prosangserver.dto.response.lyric.LyricDetailResponse;
import com.sang.prosangserver.dto.response.lyric.LyricEditDetailResponse;
import com.sang.prosangserver.dto.response.lyric.LyricInfoDetailResponse;
import com.sang.prosangserver.dto.response.lyric.info.LyricAccountInfo;
import com.sang.prosangserver.dto.response.lyric.info.LyricComment;
import com.sang.prosangserver.dto.response.lyric.info.LyricCommentBaseItem;
import com.sang.prosangserver.dto.response.lyric.info.LyricCommentUserInfo;
import com.sang.prosangserver.dto.response.lyric.info.LyricReplyComment;
import com.sang.prosangserver.enums.lyric.LyricStatuses;
import com.sang.prosangserver.models.LyricDetailModel;
import com.sang.prosangserver.models.LyricLMCommentModel;
import com.sang.prosangserver.models.LyricRepliedCommentModel;
import com.sang.prosangserver.services.LyricConvertService;
import org.mapstruct.Mapper;

import com.sang.prosangserver.dto.request.lyric.LyricRequest;
import com.sang.prosangserver.entities.lyric.Lyric;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mapper(componentModel = "spring", uses = {LyricCommentMapper.class})
public abstract class LyricMapper {

	@Autowired
	LyricCommentMapper lyricCommentMapper;

	@Autowired
	LyricConvertService lyricConvertService;

	public abstract Lyric lyricRequestToLyricEntity(LyricRequest request);

	LyricStatuses map(int value) {
		return LyricStatuses.getStatus(value);
	}

	@Mapping(target = "comments", source = ".", qualifiedByName="toComments")
	public abstract LyricDetailResponse toLyricDetail(Lyric lyric);

	@Named("toComments")
	List<LyricCommentItem> toComments(Lyric lyric) {
		return Stream.concat(lyric.getLyricAnonymousComments().stream().map(l -> lyricCommentMapper.anonymousToLyricCommentItem(l)),
			lyric.getValidComments().stream().map(l -> lyricCommentMapper.toLyricCommentItem(l)))
			.sorted(Comparator.comparing(LyricCommentItem::getCreatedDate))
			.collect(Collectors.toList());
	}

	@Mapping(target = "accountInfo", source = ".", qualifiedByName="toLyricAccountInfo")
	@Mapping(target = "comment", source = ".", qualifiedByName="toLyricComment")
	@Mapping(target = "currentOwners", source = "owners")
	public abstract LyricInfoDetailResponse lyricDetailModelToLyricInfoDetailResponse(LyricDetailModel model);
	@Named("toLyricAccountInfo")
	LyricAccountInfo toLyricAccountInfo(LyricDetailModel model) {
		LyricAccountInfo info = new LyricAccountInfo();
		info.setId(model.getAccountId());
		if (model.getName() != null) {
			info.setName(model.getName().trim());
		}
		info.setAverageRate(model.getAverageRate());
		info.setEmail(model.getEmail());
		info.setPhotoUrl(model.getPhotoUrl() != null ? model.getPhotoUrl() : LyricConstants.DEFAULT_AVATAR_URL);
		info.setUsername(model.getUsername());
		info.setLyricNumber(model.getLyricNumber());
		return info;
	}

	@Named("toLyricComment")
	LyricComment toLyricComment(LyricDetailModel model) {
		return lyricConvertService.convertCommentsStringToLyricComment(model);
	}

	@Mapping(target = "userInfo", source = ".", qualifiedByName="toLyricCommentUserInfo")
	public abstract LyricCommentBaseItem toLyricCommentBaseItem(LyricRepliedCommentModel model);

	public abstract LyricEditDetailResponse LyricEditDetailResponse(Lyric lyric);

	@Named("toLyricCommentUserInfo")
	LyricCommentUserInfo toLyricCommentUserInfo(LyricRepliedCommentModel model) {
		LyricCommentUserInfo userInfo = new LyricCommentUserInfo();
		if (model.getName() != null) {
			userInfo.setName(model.getName().trim());
		}
		userInfo.setEmail(model.getEmail());
		userInfo.setId(model.getAccountId());
		userInfo.setUsername(model.getUsername());
		if (model.getPhotoUrl() == null) {
			userInfo.setPhotoUrl(LyricConstants.DEFAULT_AVATAR_URL);
		} else {
			userInfo.setPhotoUrl(model.getPhotoUrl());
		}

		return userInfo;
	}
	@Mapping(target = "userInfo", source = ".", qualifiedByName="toLyricCommentFromLM")
	@Mapping(target = "replied", source = ".", qualifiedByName="toLyricReplyComment")
	public abstract com.sang.prosangserver.dto.response.lyric.info.LyricCommentItem toLyricCommentItemResponse(LyricLMCommentModel model);

	@Named("toLyricCommentFromLM")
	LyricCommentUserInfo toLyricCommentUserInfo(LyricLMCommentModel model) {
		return lyricConvertService.convertToLyricCommentUserInfoFromString(model.getUserInfoJson());
	}
	@Named("toLyricReplyComment")
	LyricReplyComment toLyricReplyComment(LyricLMCommentModel model) {
		LyricReplyComment replyComment = new LyricReplyComment();
		replyComment.setTotal(model.getTotalRepliedCount());
		replyComment.setHasNext(model.getTotalRepliedCount() > LyricConstants.MAX_REPLY_PER_COMMENT);
		return replyComment;
	}

}

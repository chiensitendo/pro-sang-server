package com.sang.prosangserver.mappers;

import com.sang.prosangserver.dto.response.lyric.AccountLyricItemResponse;
import com.sang.prosangserver.dto.response.lyric.LyricCommentItem;
import com.sang.prosangserver.dto.response.lyric.LyricDetailResponse;
import com.sang.prosangserver.entities.lyric.LyricComment;
import com.sang.prosangserver.enums.lyric.LyricStatuses;
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
	
}

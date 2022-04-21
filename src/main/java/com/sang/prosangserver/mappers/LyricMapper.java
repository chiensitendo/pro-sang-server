package com.sang.prosangserver.mappers;

import org.mapstruct.Mapper;

import com.sang.prosangserver.dto.request.lyric.LyricRequest;
import com.sang.prosangserver.entities.lyric.Lyric;

@Mapper(componentModel = "spring")
public interface LyricMapper {

	Lyric lyricRequestToLyricEntity(LyricRequest request);
	
}

package com.sang.prosangserver.dto.request.lyric;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LyricRequest {
	
	@NotBlank(message = "{lyric.not_blank}")
	private String title;
	
	@NotBlank(message = "{lyric.not_blank}")
	private String content;
	
	private Double stars;
	
	private String description;
}

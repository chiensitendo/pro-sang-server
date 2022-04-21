package com.sang.prosangserver.dto.response.lyric;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricItemResponse {

	private Long id;
	private String title;
	private String content;
	private Double stars;
}

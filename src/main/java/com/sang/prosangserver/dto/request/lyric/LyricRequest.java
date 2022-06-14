package com.sang.prosangserver.dto.request.lyric;

import javax.validation.constraints.NotBlank;

import com.sang.prosangserver.enums.Roles;
import com.sang.prosangserver.enums.lyric.LyricStatuses;
import com.sang.prosangserver.interfaces.EnumValidation;
import lombok.Data;

@Data
public class LyricRequest {
	
	@NotBlank(message = "{lyric.not_blank}")
	private String title;
	
	@NotBlank(message = "{lyric.not_blank}")
	private String content;

	@EnumValidation(enumClass = LyricStatuses.class, message = "{lyric.status.not_exists}")
	private Integer status = LyricStatuses.PUBLISH.getId();
	
	private Double stars;
	
	private String description;
}

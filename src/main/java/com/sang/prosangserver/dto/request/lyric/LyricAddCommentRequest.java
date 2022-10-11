package com.sang.prosangserver.dto.request.lyric;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sang.prosangserver.dto.response.lyric.info.LyricCommentUserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricAddCommentRequest {

    @NotNull(message = "{lyric.not_null}")
    @JsonProperty("account_id")
    private Long accountId;

    @NotNull(message = "{lyric.not_null}")
    @JsonProperty("content")
    private String content;

    @NotNull(message = "{lyric.not_null}")
    @JsonProperty("lyric_id")
    private Long lyricId;

    @JsonProperty("user_info")
    private LyricCommentUserInfo userInfo;
}

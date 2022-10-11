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
public class LyricRateRequest {

    @NotNull(message = "{lyric.not_null}")
    private Double stars;

    @JsonProperty("user_info")
    private LyricCommentUserInfo userInfo;
}

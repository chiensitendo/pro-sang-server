package com.sang.prosangserver.dto.request.lyric;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricLikeCommentRequest {

    @NotNull(message = "{lyric.not_null}")
    @JsonProperty("comment_id")
    private Long commentId;

    @NotNull(message = "{lyric.not_null}")
    @JsonProperty("is_liked")
    private Boolean isLiked;

    @NotNull(message = "{lyric.not_null}")
    @JsonProperty("account_id")
    private Long accountId;

}

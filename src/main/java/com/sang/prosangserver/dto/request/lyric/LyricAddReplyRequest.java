package com.sang.prosangserver.dto.request.lyric;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricAddReplyRequest extends LyricAddCommentRequest {

    @NotNull(message = "{lyric.not_null}")
    @JsonProperty("comment_id")
    private Long commentId;
}

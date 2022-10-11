package com.sang.prosangserver.dto.response.lyric;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricLikeCommentResponse {

    @JsonProperty("comment_id")
    private Long commentId;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("is_liked")
    private Boolean isLiked;

    private Long likes;
}

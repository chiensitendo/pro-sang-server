package com.sang.prosangserver.dto.response.lyric;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricLikeReplyResponse extends LyricLikeCommentResponse {

    @JsonProperty("reply_id")
    private Long replyId;

    public LyricLikeReplyResponse(Long commentId, Long accountId, Boolean isLiked, Long likes, Long replyId) {
        super(commentId, accountId, isLiked, likes);
        this.replyId = replyId;
    }

}

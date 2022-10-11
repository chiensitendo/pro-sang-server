package com.sang.prosangserver.dto.response.lyric;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sang.prosangserver.dto.response.lyric.info.LyricCommentUserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricAddReplyResponse {

    private Long id;
    @JsonProperty("comment_id")
    private Long commentId;
    private String content;
    private Long likes;
    @JsonProperty("is_liked")
    private Boolean isLiked;
    @JsonProperty("user_info")
    private LyricCommentUserInfo userInfo;
    @JsonProperty("created_date")
    private LocalDateTime createdDate;
    @JsonProperty("updated_date")
    private LocalDateTime updatedDate;

    public LyricAddReplyResponse(Long id, Long commentId, String content, LyricCommentUserInfo userInfo, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.id = id;
        this.commentId = commentId;
        this.content = content;
        this.userInfo = userInfo;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}

package com.sang.prosangserver.dto.response.lyric.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricCommentBaseItem {
    private Long id;
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
}

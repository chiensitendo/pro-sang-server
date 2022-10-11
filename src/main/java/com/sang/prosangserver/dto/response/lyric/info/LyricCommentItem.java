package com.sang.prosangserver.dto.response.lyric.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricCommentItem extends LyricCommentBaseItem {

    private Double stars;
    private LyricReplyComment replied;
    @JsonProperty("is_liked")
    private Boolean isLiked;
    @JsonProperty("total_replied_count")
    private Long totalRepliedCount;
}

package com.sang.prosangserver.dto.response.lyric;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sang.prosangserver.enums.lyric.LyricStatuses;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricDetailResponse {

    private Long id;
    private String title;
    private String content;
    private String description;
    private Double stars;
    private LyricStatuses status;
    private Boolean isDeleted = false;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private List<LyricCommentItem> comments;

    @JsonIgnore
    public LyricDetailResponse updateUserInfo(Map<Long, LyricCommentUserInfo> userInfos) {
        comments.parallelStream().forEach(comment -> {
            if (!comment.getIsAnonymous()) {
                comment.setUserInfo(userInfos.get(comment.getUserInfo().getId()));
            }
        });
        return this;
    }
}

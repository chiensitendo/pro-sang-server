package com.sang.prosangserver.dto.response.lyric;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricCommentItem {

    private Long id;
    private String content;
    private Long likes = 0L;
    private Double stars;
    private LyricCommentUserInfo userInfo;
    private Boolean isAnonymous = true;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}

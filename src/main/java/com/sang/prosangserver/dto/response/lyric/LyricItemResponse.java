package com.sang.prosangserver.dto.response.lyric;

import com.sang.prosangserver.enums.lyric.LyricStatuses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricItemResponse {

    private Long id;
    private String title;
    private String content;
    private String description;
    private Double stars;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}

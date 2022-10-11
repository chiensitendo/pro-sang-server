package com.sang.prosangserver.dto.response.lyric;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricRateResponse {

    private Double rate;
    private Double stars;
    private LyricAddCommentResponse comment;
}

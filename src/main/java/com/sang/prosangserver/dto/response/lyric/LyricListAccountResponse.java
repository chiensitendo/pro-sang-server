package com.sang.prosangserver.dto.response.lyric;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class LyricListAccountResponse {
    private Long accountId;
    private Long total;
    private List<?> lyrics;
}

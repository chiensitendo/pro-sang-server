package com.sang.prosangserver.dto.response.lyric;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LyricListResponse {

    Long total;
    List<LyricListItem> items;
}

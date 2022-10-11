package com.sang.prosangserver.dto.response.lyric.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricListBaseItem {
    private Long total;
    @JsonProperty("has_next")
    private Boolean hasNext;
}

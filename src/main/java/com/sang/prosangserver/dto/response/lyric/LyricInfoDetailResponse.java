package com.sang.prosangserver.dto.response.lyric;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sang.prosangserver.dto.response.lyric.info.LyricAccountInfo;
import com.sang.prosangserver.dto.response.lyric.info.LyricComment;
import com.sang.prosangserver.enums.lyric.LyricStatuses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricInfoDetailResponse {
    private Long id;
    private String title;
    private Float rate;
    private LyricStatuses status;
    private LyricComment comment;
    private String composers;
    @JsonProperty("current_owners")
    private String currentOwners;
    private String singers;
    private String content;
    private String description;
    @JsonProperty("account_info")
    private LyricAccountInfo accountInfo;
    @JsonProperty("is_deleted")
    private Boolean isDeleted;
    @JsonProperty("is_rated")
    private Boolean isRated;
    @JsonProperty("created_date")
    private LocalDateTime createdDate;
    @JsonProperty("updated_date")
    private LocalDateTime updatedDate;
}

package com.sang.prosangserver.dto.response.lyric;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sang.prosangserver.enums.lyric.LyricStatuses;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricEditDetailResponse {

    private Long id;

    private String title;

    private String content;

    @JsonProperty("account_id")
    private Long accountId;

    private LyricStatuses status;

    private String description;

    private String composers;

    private String singers;

    private String owners;

    private Float rate;

    @JsonProperty("is_deleted")
    private Boolean isDeleted;

    @JsonProperty("created_date")
    private LocalDateTime createdDate;

    @JsonProperty("updated_date")
    private LocalDateTime updatedDate;
}

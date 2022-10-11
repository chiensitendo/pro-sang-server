package com.sang.prosangserver.dto.response.lyric;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sang.prosangserver.dto.response.lyric.info.LyricAccountInfo;
import com.sang.prosangserver.enums.lyric.LyricStatuses;
import com.sang.prosangserver.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricListItem {

    private Long id;
    private String title;
    private Double rate;
    private LyricStatuses status;
    private Long comments;
    @JsonProperty("account_info")
    private LyricAccountInfo accountInfo;
    @JsonProperty("is_deleted")
    private Boolean isDeleted;
    @JsonProperty("created_date")
    private LocalDateTime createdDate;
    @JsonProperty("updated_date")
    private LocalDateTime updatedDate;

    public LyricListItem(Long id, String title, Double rate, LyricStatuses status, int comments, Boolean isDeleted, LocalDateTime createdDate, LocalDateTime updatedDate,
                         Long accountId, String username, String email, String accountPhotoUrl, String firstName, String lastName) {
        this.id = id;
        this.title = title;
        this.rate = rate;
        this.status = status;
        this.isDeleted = isDeleted;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        LyricAccountInfo accountInfo = new LyricAccountInfo();
        accountInfo.setId(accountId);
        accountInfo.setUsername(username);
        accountInfo.setEmail(email);
        accountInfo.setPhotoUrl(accountPhotoUrl);
        accountInfo.setName(StringUtils.getUserName(firstName, lastName));
        this.accountInfo = accountInfo;
        this.comments = Long.parseLong(String.valueOf(comments));
    }
}

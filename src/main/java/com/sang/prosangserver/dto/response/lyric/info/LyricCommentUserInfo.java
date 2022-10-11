package com.sang.prosangserver.dto.response.lyric.info;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricCommentUserInfo {

    private Long id;
    private String name;
    private String username;
    private String email;
    @JsonProperty("photo_url")
    private String photoUrl;
}

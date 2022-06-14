package com.sang.prosangserver.dto.response.lyric;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricCommentUserInfo {
    private Long id;
    private String username;
    private String email;
    public LyricCommentUserInfo (String username) {
        this.username = username;
    }
}

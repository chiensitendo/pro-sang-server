package com.sang.prosangserver.dto.response.lyric;

import com.sang.prosangserver.dto.response.lyric.info.LyricCommentBaseItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricRepliedCommentResponse {
    private Long commentId;
    private List<LyricCommentBaseItem> replies;
}

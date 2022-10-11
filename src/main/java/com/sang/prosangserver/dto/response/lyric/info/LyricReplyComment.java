package com.sang.prosangserver.dto.response.lyric.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricReplyComment extends LyricListBaseItem {
    List<LyricCommentBaseItem> comments;
}

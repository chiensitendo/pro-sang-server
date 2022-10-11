package com.sang.prosangserver.dto.response.lyric;

import com.sang.prosangserver.dto.response.lyric.info.LyricCommentItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LyricLoadMoreResponse {

    private List<LyricCommentItem> comments;
}

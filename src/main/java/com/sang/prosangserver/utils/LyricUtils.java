package com.sang.prosangserver.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sang.prosangserver.dto.response.lyric.LyricInfoDetailResponse;
import com.sang.prosangserver.dto.response.lyric.info.LyricComment;
import com.sang.prosangserver.dto.response.lyric.info.LyricCommentItem;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;

import java.util.List;
import java.util.stream.Collectors;

public class LyricUtils {
    private static final String state = "{\"blocks\":[{\"key\":\"9jakp\",\"text\":\"AAA\",\"type\":\"header-two\",\"depth\":0,\"inlineStyleRanges\":[{\"offset\":0,\"length\":3,\"style\":\"FONT_SIZE_14\"}],\"entityRanges\":[],\"data\":{}}],\"entityMap\":{}}";
    private static final List<String> comments = List.of(
        state,
        "",
        state.replace("AAA","BBB"),
        state.replace("AAA","CCC")
    );
    private static final List<Float> rates = List.of(
        null,
        3.5F,
        null,
        null
    );
    private static final List<Boolean> isLikeds = List.of(
        true,
        null,
        false,
        true
    );
    private static final List<Long> likeds = List.of(
        30L,
        null,
        0L,
        null
    );

}

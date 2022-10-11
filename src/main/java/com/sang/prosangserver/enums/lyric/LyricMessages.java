package com.sang.prosangserver.enums.lyric;

import com.sang.prosangserver.enums.EnumMessageInterface;

public enum LyricMessages implements EnumMessageInterface {

    LYRIC_NOTFOUND("lyric.not_found"),
    LYRIC_COMMENT_NOTFOUND("lyric.comment.not_found"),
    LYRIC_EXISTED_TITLE("lyric.existed.title");
    private LyricMessages(String message) {
        this.message = message;
    }

    private String message;

    @Override
    public String getMessage() {
        return this.message;
    }
}

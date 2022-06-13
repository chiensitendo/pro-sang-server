package com.sang.prosangserver.enums.lyric;

import com.sang.prosangserver.enums.EnumMessageInterface;

public enum LyricMessages implements EnumMessageInterface {

    LYRIC_NOTFOUND("lyric.not_found");
    private LyricMessages(String message) {
        this.message = message;
    }

    private String message;

    @Override
    public String getMessage() {
        return this.message;
    }
}

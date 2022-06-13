package com.sang.prosangserver.enums.lyric;

import com.fasterxml.jackson.annotation.JsonValue;

public enum LyricStatuses {

    HIDDEN(0),
    PUBLISH(1);

    private int id;

    private LyricStatuses(int id) {
        this.id = id;
    }

    @JsonValue
    public int getId() {
        return this.id;
    }

    public static LyricStatuses getStatus(int id) {
        for (LyricStatuses status: LyricStatuses.values()) {
            if (status.getId() == id) {
                return status;
            }
        }
        return null;
    }
}

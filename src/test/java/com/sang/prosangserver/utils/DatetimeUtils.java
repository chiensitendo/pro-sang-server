package com.sang.prosangserver.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatetimeUtils {
    static final String DATE_TIME_FULL_FORMAT = "yyyy-MM-dd HH:mm:ss";


    public static LocalDateTime parseFullStringToLocalDateTIme(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FULL_FORMAT);
        return LocalDateTime.parse(string, formatter);
    }
}

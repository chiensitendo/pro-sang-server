package com.sang.prosangserver.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.stream.Stream;

public class StringUtils {

    public static String generateShellUserName() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    public static String getUserName(String firstName, String lastName) {
        String name = "";
        if (firstName != null) {
            name += firstName;
        }
        name += " ";
        if (lastName != null) {
            name += lastName;
        }
        return name;
    }
}

package com.naked.logs.util;

import java.util.Map;

public class DefaultString {

    public static String emptyIfNull(Object value) {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    public static String emptyIfEmptyMap(Map<?, ?> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        return map.toString();
    }

}

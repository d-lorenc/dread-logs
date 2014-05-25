package com.naked.logs.captor;

import static com.naked.logs.util.DefaultString.emptyIfEmptyMap;
import static com.naked.logs.util.DefaultString.emptyIfNull;

import java.util.Map;

public class OnlyExpectedToStringBuilder {

    private final StringBuilder builder = new StringBuilder();

    public OnlyExpectedToStringBuilder appendIf(boolean append, String label, Class<?> clazz) {
        String className = clazz != null ? clazz.getName() : "";
        return appendIf(append, label, className);
    }

    public OnlyExpectedToStringBuilder appendIf(boolean append, String label, Map<String, ?> map) {
        return appendIf(append, label, emptyIfEmptyMap(map));
    }

    public OnlyExpectedToStringBuilder append(String label, Object value) {
        return appendIf(true, label, value);
    }

    public OnlyExpectedToStringBuilder appendIf(boolean isSet, String label, Object value) {
        if (isSet) {
            builder.append(String.format("%s:[%s] ", label, emptyIfNull(value)));
        }
        return this;
    }

    private void trimLastCharacter(StringBuilder builder) {
        int length = builder.length();
        if (length > 0) {
            builder.setLength(length - 1);
        }
    }

    @Override
    public String toString() {
        trimLastCharacter(builder);
        return builder.toString();
    }

}
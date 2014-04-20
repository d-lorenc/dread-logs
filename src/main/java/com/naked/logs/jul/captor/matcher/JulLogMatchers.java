package com.naked.logs.jul.captor.matcher;

public class JulLogMatchers {

    private JulLogMatchers() {
    }

    public static JulHasLogMatcher hasLog(String expectedMessage) {
        return new JulHasLogMatcher(expectedMessage);
    }

    public static JulNoLogMatcher noLog(String unwantedMessage) {
        return new JulNoLogMatcher(unwantedMessage);
    }

    public static JulRegexLogMatcher hasLogMatching(String messageRegex) {
        return new JulRegexLogMatcher(messageRegex);
    }

}

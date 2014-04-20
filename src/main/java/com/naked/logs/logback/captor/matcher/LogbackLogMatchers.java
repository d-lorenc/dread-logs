package com.naked.logs.logback.captor.matcher;

public class LogbackLogMatchers {

    private LogbackLogMatchers() {
    }

    public static LogbackHasLogMatcher hasLog(String expectedMessage) {
        return new LogbackHasLogMatcher(expectedMessage);
    }

    public static LogbackNoLogMatcher noLog(String unwantedMessage) {
        return new LogbackNoLogMatcher(unwantedMessage);
    }

    public static LogbackRegexLogMatcher hasLogMatching(String messageRegex) {
        return new LogbackRegexLogMatcher(messageRegex);
    }

}

package com.naked.logs.logback.captor.matcher;

import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;

import ch.qos.logback.classic.Level;

import com.naked.logs.matcher.LogExpectations;

public class LogbackMatchers {

    private LogbackMatchers() {
    }

    public static LogbackHasLogMatcher hasLog(String expectedMessage) {
        return (LogbackHasLogMatcher) new LogbackHasLogMatcher(new LogExpectations<Level>()).withMessage(equalTo(expectedMessage));
    }

    public static LogbackHasLogMatcher hasLog(Level expectedLevel, String expectedMessage) {
        return (LogbackHasLogMatcher) hasLog(expectedMessage).onLevel(expectedLevel);
    }

    public static LogbackHasLogMatcher hasLog(Matcher<String> messageMatcher) {
        return (LogbackHasLogMatcher) new LogbackHasLogMatcher(new LogExpectations<Level>()).withMessage(messageMatcher);
    }

    public static LogbackHasLogMatcher hasLog(Level expectedLevel, Matcher<String> messageMatcher) {
        return (LogbackHasLogMatcher) hasLog(messageMatcher).onLevel(expectedLevel);
    }

    public static LogbackNoLogMatcher noLog(String unwantedMessage) {
        return (LogbackNoLogMatcher) new LogbackNoLogMatcher(new LogExpectations<Level>()).withMessage(unwantedMessage);
    }

    public static LogbackNoLogMatcher noLog(Level level, String unwantedMessage) {
        return (LogbackNoLogMatcher) noLog(unwantedMessage).onLevel(level);
    }

}

package com.naked.logs.captor.jul;

import java.util.logging.Level;

import org.hamcrest.Matcher;

import com.naked.logs.captor.LogExpectations;

public class JulMatchers {

    private JulMatchers() {
    }

    public static JulHasLogMatcher hasLog(String expectedMessage) {
        return (JulHasLogMatcher) new JulHasLogMatcher(new LogExpectations<Level>()).withMessage(expectedMessage);
    }

    public static JulHasLogMatcher hasLog(Level expectedLevel, String expectedMessage) {
        return (JulHasLogMatcher) hasLog(expectedMessage).onLevel(expectedLevel);
    }

    public static JulHasLogMatcher hasLog(Matcher<String> messageMatcher) {
        return (JulHasLogMatcher) new JulHasLogMatcher(new LogExpectations<Level>()).withMessage(messageMatcher);
    }

    public static JulHasLogMatcher hasLog(Level expectedLevel, Matcher<String> messageMatcher) {
        return (JulHasLogMatcher) hasLog(messageMatcher).onLevel(expectedLevel);
    }

    public static JulNoLogMatcher noLog(String unwantedMessage) {
        return (JulNoLogMatcher) new JulNoLogMatcher(new LogExpectations<Level>()).withMessage(unwantedMessage);
    }

    public static JulNoLogMatcher noLog(Level level, String unwantedMessage) {
        return (JulNoLogMatcher) noLog(unwantedMessage).onLevel(level);
    }

    public static JulNoLogMatcher noLog(Matcher<String> messageMatcher) {
        return (JulNoLogMatcher) new JulNoLogMatcher(new LogExpectations<Level>()).withMessage(messageMatcher);
    }

    public static JulNoLogMatcher noLog(Level level, Matcher<String> messageMatcher) {
        return (JulNoLogMatcher) noLog(messageMatcher).onLevel(level);
    }

}

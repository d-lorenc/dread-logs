package com.naked.logs.log4j.captor.matcher;

import static org.hamcrest.Matchers.equalTo;

import org.apache.log4j.Level;
import org.hamcrest.Matcher;

import com.naked.logs.matcher.LogExpectations;

public class Log4jMatchers {

    private Log4jMatchers() {
    }

    public static Log4jHasLogMatcher hasLog(String expectedMessage) {
        return (Log4jHasLogMatcher) new Log4jHasLogMatcher(new LogExpectations<Level>()).withMessage(equalTo(expectedMessage));
    }

    public static Log4jHasLogMatcher hasLog(Level expectedLevel, String expectedMessage) {
        return (Log4jHasLogMatcher) hasLog(expectedMessage).onLevel(expectedLevel);
    }

    public static Log4jHasLogMatcher hasLog(Matcher<String> messageMatcher) {
        return (Log4jHasLogMatcher) new Log4jHasLogMatcher(new LogExpectations<Level>()).withMessage(messageMatcher);
    }

    public static Log4jHasLogMatcher hasLog(Level expectedLevel, Matcher<String> messageMatcher) {
        return (Log4jHasLogMatcher) hasLog(messageMatcher).onLevel(expectedLevel);
    }

    public static Log4jNoLogMatcher noLog(String unwantedMessage) {
        return new Log4jNoLogMatcher(unwantedMessage);
    }

    public static Log4jNoLogMatcher noLog(Level level, String unwantedMessage) {
        return (Log4jNoLogMatcher) noLog(unwantedMessage).onLevel(level);
    }

}

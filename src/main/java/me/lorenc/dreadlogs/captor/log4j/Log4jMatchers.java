package me.lorenc.dreadlogs.captor.log4j;

import static org.hamcrest.Matchers.equalTo;

import org.apache.log4j.Level;
import org.hamcrest.Matcher;

import me.lorenc.dreadlogs.captor.LogExpectations;

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
        return (Log4jNoLogMatcher) new Log4jNoLogMatcher(new LogExpectations<Level>()).withMessage(unwantedMessage);
    }

    public static Log4jNoLogMatcher noLog(Level level, String unwantedMessage) {
        return (Log4jNoLogMatcher) noLog(unwantedMessage).onLevel(level);
    }

    public static Log4jNoLogMatcher noLog(Matcher<String> unwantedMessageMatcher) {
        return (Log4jNoLogMatcher) new Log4jNoLogMatcher(new LogExpectations<Level>()).withMessage(unwantedMessageMatcher);
    }

    public static Log4jNoLogMatcher noLog(Level level, Matcher<String> unwantedMessageMatcher) {
        return (Log4jNoLogMatcher) noLog(unwantedMessageMatcher).onLevel(level);
    }

}

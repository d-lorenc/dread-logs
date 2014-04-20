package com.naked.logs.log4j.captor.matcher;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.naked.logs.log4j.captor.Log4jCaptor;

public class Log4jNoLogMatcher extends TypeSafeMatcher<Log4jCaptor> {

    private final String unwantedMessage;
    private Level expectedLevel = Level.ALL;

    public Log4jNoLogMatcher(String unwantedMessage) {
        this.unwantedMessage = unwantedMessage;
    }

    public void describeTo(Description description) {
        description.appendText(String.format("NO [%s] logs containing message [%s]", expectedLevel, unwantedMessage));
    }

    @Override
    protected boolean matchesSafely(Log4jCaptor captor) {
        for (LoggingEvent event : captor.getCapturedLogs()) {
            if (eventHasCorrectLevel(event) 
                    && event.getMessage().toString().contains(unwantedMessage)) {
                return false;
            }
        }
        return true;
    }

    public Log4jNoLogMatcher onLevel(Level expectedLevel) {
        this.expectedLevel = expectedLevel;
        return this;
    }

    private boolean eventHasCorrectLevel(LoggingEvent event) {
        return (expectedLevel.equals(Level.ALL) || event.getLevel().equals(expectedLevel));
    }

}

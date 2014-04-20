package com.naked.logs.logback.captor.matcher;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;

import com.naked.logs.logback.captor.LogbackCaptor;

public class LogbackNoLogMatcher extends TypeSafeMatcher<LogbackCaptor> {

    private final String unwantedMessage;
    private Level expectedLevel = Level.ALL;

    public LogbackNoLogMatcher(String unwantedMessage) {
        this.unwantedMessage = unwantedMessage;
    }

    public void describeTo(Description description) {
        description.appendText(String.format("NO [%s] logs containing message [%s]", expectedLevel, unwantedMessage));
    }

    @Override
    protected boolean matchesSafely(LogbackCaptor captor) {
        for (ILoggingEvent event : captor.getCapturedLogs()) {
            if (eventHasCorrectLevel(event)
                    && event.getMessage().toString().contains(unwantedMessage)) {
                return false;
            }
        }
        return true;
    }

    public LogbackNoLogMatcher onLevel(Level expectedLevel) {
        this.expectedLevel = expectedLevel;
        return this;
    }

    private boolean eventHasCorrectLevel(ILoggingEvent event) {
        return (expectedLevel.equals(Level.ALL) || event.getLevel().equals(expectedLevel));
    }

}

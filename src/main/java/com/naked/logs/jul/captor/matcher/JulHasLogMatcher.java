package com.naked.logs.jul.captor.matcher;

import java.util.logging.LogRecord;

import org.hamcrest.Description;

public class JulHasLogMatcher extends JulLogMatcher {

    private final String expectedMessage;

    public JulHasLogMatcher(String expectedMessage) {
        this.expectedMessage = expectedMessage;
    }

    @Override
    protected boolean matchesMessage(LogRecord event) {
        return containsMessage(event);
    }

    private boolean containsMessage(LogRecord event) {
        return event.getMessage().toString().contains(expectedMessage);
    }

    public void describeTo(Description description) {
        description.appendValue(String.format("<%s %s %s>", defaultIfNull(getExpectedLevel()), defaultIfNull(getExpectedLoggerName()), expectedMessage));
    }

}
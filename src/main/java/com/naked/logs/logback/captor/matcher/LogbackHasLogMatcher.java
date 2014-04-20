package com.naked.logs.logback.captor.matcher;

import org.hamcrest.Description;

import ch.qos.logback.classic.spi.ILoggingEvent;

public class LogbackHasLogMatcher extends LogbackLogMatcher {

    private final String expectedMessage;

    public LogbackHasLogMatcher(String expectedMessage) {
        this.expectedMessage = expectedMessage;
    }

    @Override
    protected boolean matchesMessage(ILoggingEvent event) {
        return containsMessage(event);
    }

    private boolean containsMessage(ILoggingEvent event) {
        return event.getMessage().toString().contains(expectedMessage);
    }

    public void describeTo(Description description) {
        description.appendValue(String.format("<%s %s %s>", defaultIfNull(getExpectedLevel()), defaultIfNull(getExpectedLoggerName()), expectedMessage));
    }

}
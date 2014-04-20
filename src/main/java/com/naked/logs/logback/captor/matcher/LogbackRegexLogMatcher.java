package com.naked.logs.logback.captor.matcher;

import org.hamcrest.Description;

import ch.qos.logback.classic.spi.ILoggingEvent;

public class LogbackRegexLogMatcher extends LogbackLogMatcher {

    private final String messageRegex;

    public LogbackRegexLogMatcher(String messageRegex) {
        this.messageRegex = messageRegex;
    }

    @Override
    protected boolean matchesMessage(ILoggingEvent event) {
        return matchesMessageRegex(event);
    }

    private boolean matchesMessageRegex(ILoggingEvent event) {
        return event.getMessage().toString().matches(messageRegex);
    }

    public void describeTo(Description description) {
        description.appendValue(
                String.format("<%s %s [matches: %s]>", defaultIfNull(getExpectedLevel()), defaultIfNull(getExpectedLoggerName()), messageRegex));
    }

}

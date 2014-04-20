package com.naked.logs.jul.captor.matcher;

import java.util.logging.LogRecord;

import org.hamcrest.Description;

public class JulRegexLogMatcher extends JulLogMatcher {

    private final String messageRegex;

    public JulRegexLogMatcher(String messageRegex) {
        this.messageRegex = messageRegex;
    }

    @Override
    protected boolean matchesMessage(LogRecord event) {
        return matchesMessageRegex(event);
    }

    private boolean matchesMessageRegex(LogRecord event) {
        return event.getMessage().toString().matches(messageRegex);
    }

    public void describeTo(Description description) {
        description.appendValue(
                String.format("<%s %s [matches: %s]>", defaultIfNull(getExpectedLevel()), defaultIfNull(getExpectedLoggerName()), messageRegex));
    }

}

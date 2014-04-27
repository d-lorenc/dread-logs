package com.naked.logs.logback.captor.matcher;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;

import com.naked.logs.logback.captor.LogbackCaptor;
import com.naked.logs.matcher.LogEntry;
import com.naked.logs.matcher.NoLogMatcher;

public class LogbackNoLogMatcher extends NoLogMatcher<LogbackCaptor, ILoggingEvent, Level> {

    public LogbackNoLogMatcher(String unwantedMessage) {
        super(unwantedMessage);
    }

    @Override
    protected LogEntry<Level> createLogEntry(ILoggingEvent log) {
        return new LogbackEntry(log);
    }

}

package com.naked.logs.logback.captor.matcher;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;

import com.naked.logs.logback.captor.LogbackCaptor;
import com.naked.logs.matcher.LogEntry;
import com.naked.logs.matcher.LogExpectations;
import com.naked.logs.matcher.HasLogMatcher;

public class LogbackHasLogMatcher extends HasLogMatcher<LogbackCaptor, ILoggingEvent, Level> {

    public LogbackHasLogMatcher(LogExpectations<Level> logExpectations) {
        super(logExpectations);
    }

    @Override
    protected LogEntry<Level> createLogEntry(ILoggingEvent log) {
        return new LogbackEntry(log);
    }

}
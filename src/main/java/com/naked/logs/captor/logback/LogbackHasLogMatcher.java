package com.naked.logs.captor.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;

import com.naked.logs.captor.HasLogMatcher;
import com.naked.logs.captor.LogEntry;
import com.naked.logs.captor.LogExpectations;

public class LogbackHasLogMatcher extends HasLogMatcher<LogbackCaptor, ILoggingEvent, Level> {

    public LogbackHasLogMatcher(LogExpectations<Level> logExpectations) {
        super(logExpectations);
    }

    @Override
    protected LogEntry<Level> createLogEntry(ILoggingEvent log) {
        return new LogbackEntry(log);
    }

}
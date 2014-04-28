package com.naked.logs.captor.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;

import com.naked.logs.captor.LogEntry;
import com.naked.logs.captor.LogExpectations;
import com.naked.logs.captor.NoLogMatcher;

public class LogbackNoLogMatcher extends NoLogMatcher<LogbackCaptor, ILoggingEvent, Level> {

    public LogbackNoLogMatcher(LogExpectations<Level> logExpectations) {
        super(logExpectations);
    }

    @Override
    protected LogEntry<Level> createLogEntry(ILoggingEvent log) {
        return new LogbackEntry(log);
    }

}

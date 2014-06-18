package me.lorenc.dreadlogs.captor.logback;

import me.lorenc.dreadlogs.captor.HasLogMatcher;
import me.lorenc.dreadlogs.captor.LogEntry;
import me.lorenc.dreadlogs.captor.LogExpectations;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class LogbackHasLogMatcher extends HasLogMatcher<LogbackCaptor, ILoggingEvent, Level> {

    public LogbackHasLogMatcher(LogExpectations<Level> logExpectations) {
        super(logExpectations);
    }

    @Override
    protected LogEntry<Level> createLogEntry(ILoggingEvent log) {
        return new LogbackEntry(log);
    }

}
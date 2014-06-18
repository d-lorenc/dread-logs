package me.lorenc.dreadlogs.captor.logback;

import me.lorenc.dreadlogs.captor.LogEntry;
import me.lorenc.dreadlogs.captor.LogExpectations;
import me.lorenc.dreadlogs.captor.NoLogMatcher;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class LogbackNoLogMatcher extends NoLogMatcher<LogbackCaptor, ILoggingEvent, Level> {

    public LogbackNoLogMatcher(LogExpectations<Level> logExpectations) {
        super(logExpectations);
    }

    @Override
    protected LogEntry<Level> createLogEntry(ILoggingEvent log) {
        return new LogbackEntry(log);
    }

}

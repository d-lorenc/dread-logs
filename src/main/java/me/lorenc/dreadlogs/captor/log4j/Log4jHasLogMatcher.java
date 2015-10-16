package me.lorenc.dreadlogs.captor.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import me.lorenc.dreadlogs.captor.HasLogMatcher;
import me.lorenc.dreadlogs.captor.LogEntry;
import me.lorenc.dreadlogs.captor.LogExpectations;

public class Log4jHasLogMatcher extends HasLogMatcher<Log4jCaptor, LoggingEvent, Level> {

    public Log4jHasLogMatcher(LogExpectations<Level> logExpectations) {
        super(logExpectations);
    }

    @Override
    protected LogEntry<Level> createLogEntry(LoggingEvent log) {
        return new Log4jEntry(log);
    }

}
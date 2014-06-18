package me.lorenc.dreadlogs.captor.log4j;

import me.lorenc.dreadlogs.captor.LogEntry;
import me.lorenc.dreadlogs.captor.LogExpectations;
import me.lorenc.dreadlogs.captor.NoLogMatcher;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

public class Log4jNoLogMatcher extends NoLogMatcher<Log4jCaptor, LoggingEvent, Level> {

    public Log4jNoLogMatcher(LogExpectations<Level> logExpectations) {
        super(logExpectations);
    }

    @Override
    protected LogEntry<Level> createLogEntry(LoggingEvent log) {
        return new Log4jEntry(log);
    }

}

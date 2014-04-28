package com.naked.logs.captor.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import com.naked.logs.captor.HasLogMatcher;
import com.naked.logs.captor.LogEntry;
import com.naked.logs.captor.LogExpectations;

public class Log4jHasLogMatcher extends HasLogMatcher<Log4jCaptor, LoggingEvent, Level> {

    public Log4jHasLogMatcher(LogExpectations<Level> logExpectations) {
        super(logExpectations);
    }

    @Override
    protected LogEntry<Level> createLogEntry(LoggingEvent log) {
        return new Log4jEntry(log);
    }

}
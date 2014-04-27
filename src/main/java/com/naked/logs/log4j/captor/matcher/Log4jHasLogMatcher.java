package com.naked.logs.log4j.captor.matcher;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import com.naked.logs.log4j.captor.Log4jCaptor;
import com.naked.logs.matcher.LogEntry;
import com.naked.logs.matcher.LogExpectations;
import com.naked.logs.matcher.LogMatcher;

public class Log4jHasLogMatcher extends LogMatcher<Log4jCaptor, LoggingEvent, Level> {

    public Log4jHasLogMatcher(LogExpectations<Level> logExpectations) {
        super(logExpectations);
    }

    @Override
    protected LogEntry<Level> createLogEntry(LoggingEvent log) {
        return new Log4jEntry(log);
    }

}
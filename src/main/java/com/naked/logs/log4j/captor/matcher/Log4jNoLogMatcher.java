package com.naked.logs.log4j.captor.matcher;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import com.naked.logs.log4j.captor.Log4jCaptor;
import com.naked.logs.matcher.LogEntry;
import com.naked.logs.matcher.NoLogMatcher;

public class Log4jNoLogMatcher extends NoLogMatcher<Log4jCaptor, LoggingEvent, Level> {

    public Log4jNoLogMatcher(String unwantedMessage) {
        super(unwantedMessage);
    }

    @Override
    protected LogEntry<Level> createLogEntry(LoggingEvent log) {
        return new Log4jEntry(log);
    }

}

package com.naked.logs.jul.captor.matcher;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.naked.logs.jul.captor.JulCaptor;
import com.naked.logs.matcher.LogEntry;
import com.naked.logs.matcher.LogExpectations;
import com.naked.logs.matcher.HasLogMatcher;

public class JulHasLogMatcher extends HasLogMatcher<JulCaptor, LogRecord, Level> {

    public JulHasLogMatcher(LogExpectations<Level> logExpectations) {
        super(logExpectations);
    }

    @Override
    protected LogEntry<Level> createLogEntry(LogRecord log) {
        return new JulEntry(log);
    }

}
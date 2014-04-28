package com.naked.logs.captor.jul;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.naked.logs.captor.HasLogMatcher;
import com.naked.logs.captor.LogEntry;
import com.naked.logs.captor.LogExpectations;

public class JulHasLogMatcher extends HasLogMatcher<JulCaptor, LogRecord, Level> {

    public JulHasLogMatcher(LogExpectations<Level> logExpectations) {
        super(logExpectations);
    }

    @Override
    protected LogEntry<Level> createLogEntry(LogRecord log) {
        return new JulEntry(log);
    }

}
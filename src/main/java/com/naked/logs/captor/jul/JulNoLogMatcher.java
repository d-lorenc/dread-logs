package com.naked.logs.captor.jul;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.naked.logs.captor.LogEntry;
import com.naked.logs.captor.LogExpectations;
import com.naked.logs.captor.NoLogMatcher;

public class JulNoLogMatcher extends NoLogMatcher<JulCaptor, LogRecord, Level> {

    public JulNoLogMatcher(LogExpectations<Level> logExpectations) {
        super(logExpectations);
    }

    @Override
    protected LogEntry<Level> createLogEntry(LogRecord log) {
        return new JulEntry(log);
    }

}

package me.lorenc.dreadlogs.captor.jul;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import me.lorenc.dreadlogs.captor.HasLogMatcher;
import me.lorenc.dreadlogs.captor.LogEntry;
import me.lorenc.dreadlogs.captor.LogExpectations;

public class JulHasLogMatcher extends HasLogMatcher<JulCaptor, LogRecord, Level> {

    public JulHasLogMatcher(LogExpectations<Level> logExpectations) {
        super(logExpectations);
    }

    @Override
    protected LogEntry<Level> createLogEntry(LogRecord log) {
        return new JulEntry(log);
    }

}
package me.lorenc.dreadlogs.captor.jul;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import me.lorenc.dreadlogs.captor.LogEntry;
import me.lorenc.dreadlogs.captor.LogExpectations;
import me.lorenc.dreadlogs.captor.NoLogMatcher;

public class JulNoLogMatcher extends NoLogMatcher<JulCaptor, LogRecord, Level> {

    public JulNoLogMatcher(LogExpectations<Level> logExpectations) {
        super(logExpectations);
    }

    @Override
    protected LogEntry<Level> createLogEntry(LogRecord log) {
        return new JulEntry(log);
    }

}

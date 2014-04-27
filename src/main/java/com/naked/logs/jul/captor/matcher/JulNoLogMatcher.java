package com.naked.logs.jul.captor.matcher;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.naked.logs.jul.captor.JulCaptor;
import com.naked.logs.matcher.LogEntry;
import com.naked.logs.matcher.NoLogMatcher;

public class JulNoLogMatcher extends NoLogMatcher<JulCaptor, LogRecord, Level> {

    public JulNoLogMatcher(String unwantedMessage) {
        super(unwantedMessage);
    }

    @Override
    protected LogEntry<Level> createLogEntry(LogRecord log) {
        return new JulEntry(log);
    }

}

package com.naked.logs.jul.captor.matcher;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.naked.logs.matcher.LogEntry;

public class JulEntry implements LogEntry<Level>{

    private final LogRecord logRecord;

    public JulEntry(LogRecord logRecord) {
        this.logRecord = logRecord;
    }

    public Level getLevel() {
        return logRecord.getLevel();
    }

    public String getMessage() {
        return logRecord.getMessage();
    }

    public String getLoggerName() {
        return logRecord.getLoggerName();
    }

    public String getExceptionMessage() {
        Throwable thrown = logRecord.getThrown();
        if (thrown == null) {
            return null;
        }
        return thrown.getMessage();
    }

    public String getExceptionClassName() {
        Throwable thrown = logRecord.getThrown();
        if (thrown == null) {
            return null;
        }

        return thrown.getClass().getName();
    }

    public String getMdcValue(String mdcKey) {
        return null;
    }

}

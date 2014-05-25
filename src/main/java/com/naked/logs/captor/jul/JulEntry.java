package com.naked.logs.captor.jul;

import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.naked.logs.captor.LogEntry;

public class JulEntry extends LogEntry<Level>{

    private final LogRecord logRecord;

    public JulEntry(LogRecord logRecord) {
        this.logRecord = logRecord;
    }

    @Override
    public Level getLevel() {
        return logRecord.getLevel();
    }

    @Override
    public String getMessage() {
        return logRecord.getMessage();
    }

    @Override
    public String getLoggerName() {
        return logRecord.getLoggerName();
    }

    @Override
    public String getExceptionMessage() {
        Throwable thrown = logRecord.getThrown();
        if (thrown == null) {
            return null;
        }
        return thrown.getMessage();
    }

    @Override
    public String getExceptionClassName() {
        Throwable thrown = logRecord.getThrown();
        if (thrown == null) {
            return null;
        }

        return thrown.getClass().getName();
    }
    
    @Override
    public Map<String, Object> getMdcMap() {
        return Collections.emptyMap();
    }

    @Override
    public String getMdcValue(String mdcKey) {
        return null;
    }

    @Override
    public boolean isMdcSupported() {
        return false;
    }

}

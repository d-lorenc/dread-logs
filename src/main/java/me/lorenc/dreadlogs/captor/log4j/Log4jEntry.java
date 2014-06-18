package me.lorenc.dreadlogs.captor.log4j;

import java.util.Map;

import me.lorenc.dreadlogs.captor.LogEntry;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

public class Log4jEntry extends LogEntry<Level> {

    private final LoggingEvent loggingEvent;

    public Log4jEntry(LoggingEvent loggingEvent) {
        this.loggingEvent = loggingEvent;
    }

    @Override
    public String getMessage() {
        return loggingEvent.getMessage().toString();
    }

    @Override
    public String getLoggerName() {
        return loggingEvent.getLoggerName();
    }

    @Override
    public Level getLevel() {
        return loggingEvent.getLevel();
    }

    @Override
    public String getExceptionMessage() {
        Throwable throwable = getThrowable();
        if (throwable == null) {
            return null;
        }
        return throwable.getMessage();
    }

    @Override
    public String getExceptionClassName() {
        Throwable throwable = getThrowable();
        if (throwable == null) {
            return null;
        }
        return throwable.getClass().getName();
    }

    private Throwable getThrowable() {
        ThrowableInformation throwableInformation = loggingEvent.getThrowableInformation();
        if (throwableInformation == null) {
            return null;
        }
        Throwable throwable = throwableInformation.getThrowable();
        return throwable;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getMdcMap() {
        return loggingEvent.getProperties();
    }
    
    @Override
    public String getMdcValue(String mdcKey) {
        Object mdcValue = loggingEvent.getMDC(mdcKey);
        if (mdcValue == null) {
            return null;
        }
        return mdcValue.toString();
    }
    
    @Override
    public boolean isMdcSupported() {
        return true;
    }

}

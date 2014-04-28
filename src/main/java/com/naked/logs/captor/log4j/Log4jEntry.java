package com.naked.logs.captor.log4j;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

import com.naked.logs.captor.LogEntry;

public class Log4jEntry implements LogEntry<Level> {

    private final LoggingEvent loggingEvent;

    public Log4jEntry(LoggingEvent loggingEvent) {
        this.loggingEvent = loggingEvent;
    }

    public String getMessage() {
        return loggingEvent.getMessage().toString();
    }

    public String getLoggerName() {
        return loggingEvent.getLoggerName();
    }

    public Level getLevel() {
        return loggingEvent.getLevel();
    }

    public String getExceptionMessage() {
        Throwable throwable = getThrowable();
        if (throwable == null) {
            return null;
        }
        return throwable.getMessage();
    }

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

    public String getMdcValue(String mdcKey) {
        Object mdcValue = loggingEvent.getMDC(mdcKey);
        if (mdcValue == null) {
            return null;
        }
        return mdcValue.toString();
    }

}

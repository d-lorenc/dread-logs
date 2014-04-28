package com.naked.logs.captor.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;

import com.naked.logs.captor.LogEntry;

public class LogbackEntry implements LogEntry<Level> {

    private final ILoggingEvent loggingEvent;

    public LogbackEntry(ILoggingEvent loggingEvent) {
        this.loggingEvent = loggingEvent;
    }

    public String getMessage() {
        return loggingEvent.getMessage();
    }

    public String getLoggerName() {
        return loggingEvent.getLoggerName();
    }

    public Level getLevel() {
        return loggingEvent.getLevel();
    }

    public String getExceptionMessage() {
        IThrowableProxy throwableProxy = loggingEvent.getThrowableProxy();
        if (throwableProxy == null) {
            return null;
        }
        return throwableProxy.getMessage();
    }

    public String getExceptionClassName() {
        IThrowableProxy throwableProxy = loggingEvent.getThrowableProxy();
        if (throwableProxy == null) {
            return null;
        }
        return throwableProxy.getClassName();
    }

    public String getMdcValue(String mdcKey) {
        return loggingEvent.getMDCPropertyMap().get(mdcKey);
    }

}

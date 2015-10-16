package me.lorenc.dreadlogs.captor.logback;

import java.util.Map;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import me.lorenc.dreadlogs.captor.LogEntry;

public class LogbackEntry extends LogEntry<Level> {

    private final ILoggingEvent loggingEvent;

    public LogbackEntry(ILoggingEvent loggingEvent) {
        this.loggingEvent = loggingEvent;
    }

    @Override
    public String getMessage() {
        return loggingEvent.getMessage();
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
        IThrowableProxy throwableProxy = loggingEvent.getThrowableProxy();
        if (throwableProxy == null) {
            return null;
        }
        return throwableProxy.getMessage();
    }

    @Override
    public String getExceptionClassName() {
        IThrowableProxy throwableProxy = loggingEvent.getThrowableProxy();
        if (throwableProxy == null) {
            return null;
        }
        return throwableProxy.getClassName();
    }

    @Override
    public Map<String, ?> getMdcMap() {
        return loggingEvent.getMDCPropertyMap();
    }

    @Override
    public String getMdcValue(String mdcKey) {
        return loggingEvent.getMDCPropertyMap().get(mdcKey);
    }

    @Override
    public boolean isMdcSupported() {
        return true;
    }

}

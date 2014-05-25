package com.naked.logs.captor;

import java.util.Map;

public abstract class LogEntry<LEVEL> {

    public abstract LEVEL getLevel();
    public abstract String getMessage();
    public abstract String getLoggerName();
    public abstract String getExceptionMessage();
    public abstract String getExceptionClassName();
    public abstract Map<String, ?> getMdcMap();
    public abstract String getMdcValue(String mdcKey);
    public abstract boolean isMdcSupported();

    @Override
    public String toString() {
        return new OnlyExpectedToStringBuilder()
            .append("Level", getLevel())
            .append("LoggerName", getLoggerName())
            .appendIf(isMdcSupported(), "MDC", getMdcMap())
            .append("ExceptionClass", getExceptionClassName())
            .append("ExceptionMessage", getExceptionMessage())
            .append("Message", getMessage())
            .toString();
    }

    public String toStringOnlyExpected(LogExpectations<LEVEL> logExpectations) {
        return new OnlyExpectedToStringBuilder()
            .appendIf(logExpectations.isLevelSet(), "Level", getLevel())
            .appendIf(logExpectations.isLogerNameSet(), "LoggerName", getLoggerName())
            .appendIf(isMdcSupported() && logExpectations.isMdcSet(), "MDC", getMdcMap())
            .appendIf(logExpectations.isExceptionClassSet(), "ExceptionClass", getExceptionClassName())
            .appendIf(logExpectations.isExceptionMessageSet(), "ExceptionMessage", getExceptionMessage())
            .appendIf(logExpectations.isMessageSet(), "Message", getMessage())
            .toString();
    }

}

package com.naked.logs.matcher;

public interface LogEntry<LEVEL> {

    LEVEL getLevel();
    String getMessage();
    String getLoggerName();
    String getExceptionMessage();
    String getExceptionClassName();
    String getMdcValue(String mdcKey);

}

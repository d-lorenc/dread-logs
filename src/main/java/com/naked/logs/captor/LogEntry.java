package com.naked.logs.captor;

public interface LogEntry<LEVEL> {

    LEVEL getLevel();
    String getMessage();
    String getLoggerName();
    String getExceptionMessage();
    String getExceptionClassName();
    String getMdcValue(String mdcKey);

}

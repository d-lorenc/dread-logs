package com.naked.logs.log4j;

import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

public class Log4jSimpleLayout extends Layout {

    @Override
    public String format(LoggingEvent event) {
        return String.format("%s %s %s", event.getLevel().toString(), event.getLoggerName(), event.getMessage());
    }

    @Override
    public boolean ignoresThrowable() {
        return false;
    }

    public void activateOptions() {
    }

}

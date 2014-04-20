package com.naked.logs.logback.captor;

import java.util.List;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class LogbackCaptor {

    private final LogbackCapturingAppender appender;
    private final Logger logger;

    public LogbackCaptor(String loggerName) {
        this(loggerName, Level.ALL);
    }

    public LogbackCaptor(String loggerName, Level level) {
        this((Logger) LoggerFactory.getLogger(loggerName), new LogbackCapturingAppender(), level);
    }

    LogbackCaptor(Logger logger, LogbackCapturingAppender appender, Level level) {
        this.logger = logger;
        this.appender = appender;

        logger.setLevel(level);
        attachAppender(logger, appender);
    }

    private void attachAppender(Logger logger, LogbackCapturingAppender appender) {
        logger.addAppender(appender);
        appender.start();
    }

    public void detachAppender() {
        logger.detachAppender(appender);
    }

    public List<ILoggingEvent> getCapturedLogs() {
        return appender.getCapturedLogs();
    }

}
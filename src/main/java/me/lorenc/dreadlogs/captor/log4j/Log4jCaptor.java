package me.lorenc.dreadlogs.captor.log4j;

import java.util.List;

import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.rules.ExternalResource;

import me.lorenc.dreadlogs.captor.Captor;
import me.lorenc.dreadlogs.captor.LogCapture;

public class Log4jCaptor extends ExternalResource implements Captor<LoggingEvent> {

    private final Log4jCapturingAppender appender;
    private final Logger logger;

    public Log4jCaptor(String loggerName) {
        this(loggerName, Level.ALL);
    }

    public Log4jCaptor(String loggerName, Level level) {
        this(loggerName, level, new Log4jSimpleLayout());
    }

    Log4jCaptor(String loggerName, Level level, Layout layout) {
        this(Logger.getLogger(loggerName), new Log4jCapturingAppender(layout, new LogCapture<LoggingEvent>()), level);
    }

    Log4jCaptor(Logger logger, Log4jCapturingAppender appender, Level level) {
        this.logger = logger;
        this.appender = appender;

        logger.setLevel(level);
        attachAppender(logger, appender);
    }

    private void attachAppender(Logger logger, Log4jCapturingAppender appender) {
        logger.addAppender(appender);
    }

    public void detachAppender() {
        logger.removeAppender(appender);
    }

    public List<LoggingEvent> getCapturedLogs() {
        return appender.getCapturedLogs();
    }

    @Override
    protected void after() {
        detachAppender();
    }

    @Override
    public String toString() {
        return appender.toString();
    }

}
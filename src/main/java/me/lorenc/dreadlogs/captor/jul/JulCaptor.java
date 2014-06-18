package me.lorenc.dreadlogs.captor.jul;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import me.lorenc.dreadlogs.captor.Captor;
import me.lorenc.dreadlogs.captor.LogCapture;

public class JulCaptor implements Captor<LogRecord> {

    private final JulCapturingHandler handler;
    private final Logger logger;

    public JulCaptor(String loggerName) {
        this(loggerName, Level.ALL);
    }

    public JulCaptor(String loggerName, Level level) {
        this(Logger.getLogger(loggerName), new JulCapturingHandler(new LogCapture<LogRecord>()), level);
    }

    JulCaptor(Logger logger, JulCapturingHandler handler, Level level) {
        this.logger = logger;
        this.handler = handler;

        logger.setLevel(level);
        attachAppender(logger, handler);
//        logger.setUseParentHandlers(false);
    }

    private void attachAppender(Logger logger, JulCapturingHandler handler) {
        logger.addHandler(handler);
    }

    public void detachAppender() {
        logger.removeHandler(handler);
    }

    public List<LogRecord> getCapturedLogs() {
        return handler.getCapturedLogs();
    }

}
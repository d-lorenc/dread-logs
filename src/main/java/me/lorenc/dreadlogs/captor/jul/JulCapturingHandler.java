package me.lorenc.dreadlogs.captor.jul;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import me.lorenc.dreadlogs.captor.LogCapture;

public class JulCapturingHandler extends Handler {

    private final LogCapture<LogRecord> logCapture;

    public JulCapturingHandler(LogCapture<LogRecord> logCapture) {
        this.logCapture = logCapture;
    }

    @Override
    public void publish(LogRecord logRecord) {
        logCapture.capture(logRecord);
    }

    public void reset() {
        logCapture.reset();
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
    }

    public List<LogRecord> getCapturedLogs() {
        return logCapture.getCapturedLogs();
    }

}

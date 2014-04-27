package com.naked.logs.log4j.captor;

import java.util.List;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

import com.naked.logs.captor.LogCapture;

public class Log4jCapturingAppender extends AppenderSkeleton {

    private final LogCapture<LoggingEvent> logCapture;

    public Log4jCapturingAppender(Layout layout, LogCapture<LoggingEvent> logCapture) {
        this.layout = layout;
        this.logCapture = logCapture;
    }

    @Override
    protected void append(LoggingEvent loggingEvent) {
        loggingEvent.getMDCCopy();
        logCapture.capture(loggingEvent);
    }

	public void close() {
	}

	public boolean requiresLayout() {
		return false;
	}

    public void reset() {
        logCapture.reset();
    }

    List<LoggingEvent> getCapturedLogs() {
        return logCapture.getCapturedLogs();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (LoggingEvent event : getCapturedLogs()) {
            builder.append(layout.format(event));
        }
        return builder.toString();
    }

}
